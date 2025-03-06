package site.nansan.refresh.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.nansan.login.util.JWTUtil;
import site.nansan.login.util.UtilFunction;
import site.nansan.refresh.exception.RefreshTokenInvalidException;
import site.nansan.refresh.exception.RefreshTokenNotExistException;
import site.nansan.refresh.repository.RefreshRepository;
import site.nansan.user.domain.Users;
import site.nansan.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JWTUtil jwtUtil;
    private final UtilFunction utilFunction;
    private final UserRepository userRepository;
    private final RefreshRepository refreshRepository;

    @Transactional
    public String reissueToken(String refreshToken, HttpServletResponse response) {

        if (refreshToken == null)
            throw new RefreshTokenNotExistException();
        if (!"refresh".equals(jwtUtil.getCategory(refreshToken)) || !refreshRepository.existsByRefresh(refreshToken)) {
            throw new RefreshTokenInvalidException();
        }

        Long userId = jwtUtil.getUserId(refreshToken);
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        String newAccessToken = jwtUtil.createJwt("access", user.getNickName(), user.getRole(), 30000L, user.getId());
        String newRefreshToken = jwtUtil.createJwt("refresh", user.getNickName(), user.getRole(), 86400000L, user.getId());

        refreshRepository.deleteByRefresh(refreshToken);
        utilFunction.addRefreshEntity(user.getPlatformId(), newRefreshToken);

        response.setHeader("refresh", newRefreshToken);
        response.setStatus(HttpStatus.OK.value());

        return newAccessToken;
    }
}