package site.nansan.login.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.nansan.refresh.domain.Refresh;
import site.nansan.refresh.repository.RefreshRepository;

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
}
