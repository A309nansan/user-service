package site.nansan.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeacherCodeService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String TEACHER_CODE_PREFIX = "teacher:code:";
    private static final long TEACHER_CODE_TTL = 24; // 유효기간 24시간

    // 선생님 코드 생성 및 Redis에 저장
    // @param userId 사용자 ID
    // @return 생성된 코드
    public String generateTeacherCode(Long userId) {
        String code = generateUniqueCode();

        // Redis에 저장: "teacher:code:{userId}" -> "{code}" with 24 hour TTL
        String key = TEACHER_CODE_PREFIX + userId;
        redisTemplate.opsForValue().set(key, code, TEACHER_CODE_TTL, TimeUnit.HOURS);

        log.info("Teacher code generated for user {}: {} (valid for {} hours)", userId, code, TEACHER_CODE_TTL);
        return code;
    }

    // 선생님 코드 조회
    public String getTeacherCode(Long userId) {
        String key = TEACHER_CODE_PREFIX + userId;
        return redisTemplate.opsForValue().get(key);
    }

    // 선생님 코드 검증 (선생님 추가할 때 사용)
    public boolean validateTeacherCode(String code) {
        return isCodeExists(code);
    }

    // 중복되지 않는 고유한 코드 생성
    private String generateUniqueCode() {
        String code;

        do {
            code = UUID.randomUUID().toString().substring(0, 8);

            // 코드가 이미 존재하는지 확인
            if (!isCodeExists(code)) {
                return code;
            }

            log.info("Code collision detected: {}, trying again...", code);
        } while (true);
    }

    // 코드가 이미 Redis에 존재하는지 확인
    private boolean isCodeExists(String code) {
        return redisTemplate.keys(TEACHER_CODE_PREFIX + "*").stream()
                .anyMatch(key -> code.equals(redisTemplate.opsForValue().get(key)));
    }

    // 코드로 선생님 ID 찾기
    public Long findTeacherIdByCode(String code) {
        for (String key : redisTemplate.keys(TEACHER_CODE_PREFIX + "*")) {
            String storedCode = redisTemplate.opsForValue().get(key);
            if (code.equals(storedCode)) {
                // "teacher:code:{userId}" 형식에서 userId 추출
                String userId = key.substring(TEACHER_CODE_PREFIX.length());
                try {
                    return Long.parseLong(userId);
                } catch (NumberFormatException e) {
                    log.error("잘못된 키 형식: {}", key, e);
                    return null;
                }
            }
        }
        return null;
    }
}