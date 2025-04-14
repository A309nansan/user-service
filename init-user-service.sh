#!/bin/bash

# 명령어 실패 시 스크립트 종료
set -euo pipefail

# 로그 출력 함수
log() {
  echo "[$(date +'%Y-%m-%d %H:%M:%S')] $*"
}

# 에러 발생 시 로그와 함께 종료하는 함수
error() {
  log "Error on line $1"
  exit 1
}

trap 'error $LINENO' ERR

log "스크립트 실행 시작."

cd user || { echo "디렉토리 변경 실패"; exit 1; }

# docker network 생성
if docker network ls --format '{{.Name}}' | grep -q '^nansan-network$'; then
  log "Docker network named 'nansan-network' is already existed."
else
  log "Docker network named 'nansan-network' is creating..."
  docker network create --driver bridge nansan-network
fi

# 필요한 환경변수를 Vault에서 가져오기
log "Get credential data from vault..."

TOKEN_RESPONSES=$(curl -s --request POST \
  --data "{\"role_id\":\"${ROLE_ID}\", \"secret_id\":\"${SECRET_ID}\"}" \
  https://vault.nansan.site/v1/auth/approle/login)

CLIENT_TOKEN=$(echo "$TOKEN_RESPONSES" | jq -r '.auth.client_token')

SECRET_RESPONSE=$(curl -s --header "X-Vault-Token: ${CLIENT_TOKEN}" \
  --request GET https://vault.nansan.site/v1/kv/data/auth)

MONGODB_HOST=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.mongodb.private.host')
MONGODB_PORT=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.mongodb.private.port')
MONGODB_USERNAME=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.mongodb.username')
MONGODB_PASSWORD=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.mongodb.password')
REDIS_HOST=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.redis.private.host')
REDIS_PORT=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.redis.private.port')
REDIS_PASSWORD=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.redis.password')
MYSQL_HOST=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.mysql.private.host')
MYSQL_PORT=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.mysql.private.port')
MYSQL_USERNAME=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.mysql.username')
MYSQL_PASSWORD=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.mysql.password')
CONFIG_SERVER_URI=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.configserver.private.uri')
CONFIG_SERVER_USERNAME=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.configserver.username')
CONFIG_SERVER_PASSWORD=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.configserver.password')
RABBITMQ_HOST=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.rabbitmq.server.host')
RABBITMQ_PORT=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.rabbitmq.server.port')
RABBITMQ_USERNAME=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.rabbitmq.username')
RABBITMQ_PASSWORD=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.rabbitmq.password')
MINIO_ACCESS_KEY=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.minio.username')
MINIO_SECRET_KEY=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.minio.password')
JWT_SECRET=$(echo "$SECRET_RESPONSE" | jq -r '.data.data.apigateway.jwt')

# Build Gradle
log "build gradle"
./gradlew clean build

# 기존 서비스 삭제
log "user-service undeploy"
docker rm -f user-service

# 기존 user-service 이미지를 삭제하고 새로 빌드
log "user-service image remove and build."
docker rmi user-service:latest || true
docker build -t user-service:latest .

# Docker로 user-service 서비스 실행
log "Execute user-service..."
docker run -d \
  --name user-service \
  --restart unless-stopped \
  -e MONGODB_HOST=${MONGODB_HOST} \
  -e MONGODB_PORT=${MONGODB_PORT} \
  -e MONGODB_USERNAME=${MONGODB_USERNAME} \
  -e MONGODB_PASSWORD=${MONGODB_PASSWORD} \
  -e REDIS_HOST=${REDIS_HOST} \
  -e REDIS_PORT=${REDIS_PORT} \
  -e REDIS_PASSWORD=$REDIS_PASSWORD} \
  -e MYSQL_HOST=${MYSQL_HOST} \
  -e MYSQL_PORT=${MYSQL_PORT} \
  -e MYSQL_USERNAME=${MYSQL_USERNAME} \
  -e MYSQL_PASSWORD=${MYSQL_PASSWORD} \
  -e CONFIG_SERVER_URI=${CONFIG_SERVER_URI} \
  -e CONFIG_SERVER_USERNAME=${CONFIG_SERVER_USERNAME} \
  -e CONFIG_SERVER_PASSWORD=${CONFIG_SERVER_PASSWORD} \
  -e RABBITMQ_HOST=${RABBITMQ_HOST} \
  -e RABBITMQ_PORT=${RABBITMQ_PORT} \
  -e RABBITMQ_USERNAME=${RABBITMQ_USERNAME} \
  -e RABBITMQ_PASSWORD=${RABBITMQ_PASSWORD} \
  -e MINIO_ACCESS_KEY=${MINIO_ACCESS_KEY} \
  -e MINIO_SECRET_KEY=${MINIO_SECRET_KEY} \
  -e JWT_SECRET=${JWT_SECRET} \
  --network nansan-network \
  user-service:latest

echo "==== Succeed!!! ===="
