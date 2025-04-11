package site.nansan.login.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.nansan.refresh.domain.Refresh;
import site.nansan.refresh.repository.RefreshRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class UtilFunction {

    private final RefreshRepository refreshRepository;

    public void addRefreshEntity(String platformId, String token) {
        Timestamp expiration = new Timestamp(System.currentTimeMillis() + 86400000L);

        Refresh refreshEntity = Refresh.builder()
                .platformId(platformId)
                .refresh(token)
                .expiration(expiration)
                .build();

        refreshRepository.save(refreshEntity);
    }

    public String generateHashIdFromPlatformId(String platformId) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(platformId.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString(); // 64자리 해시
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 알고리즘을 사용할 수 없습니다.", e);
        }
    }


}
