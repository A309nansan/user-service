package site.nansan.login.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import site.nansan.login.util.JWTUtil;
import site.nansan.login.util.UtilFunction;
import site.nansan.refresh.repository.RefreshRepository;
import site.nansan.user.domain.Role;
import site.nansan.user.domain.SocialPlatform;
import site.nansan.user.domain.Users;
import site.nansan.login.dto.JoinRequestDto;
import site.nansan.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JWTUtil jwtUtil;
    private final UtilFunction utilFunction;
    private final UserRepository userRepository;
    private final RefreshRepository refreshRepository;

    public String joinOrGetUser(JoinRequestDto joinDTO, HttpServletResponse response) {
        Users user = userRepository.findByPlatformId(joinDTO.getPlatformId())
                .orElseGet(() -> {
                    Users newUser = Users.builder()
                            .platformId(joinDTO.getPlatformId())
                            .email(joinDTO.getEmail())
                            .nickName(joinDTO.getNickName())
                            .socialPlatform(SocialPlatform.valueOf(joinDTO.getSocialPlatform().toUpperCase()))
                            .role(Role.USER)
                            .build();
                    return userRepository.save(newUser);
                });

        String access = jwtUtil.createJwt("access", user.getNickName(), user.getRole(), 600000L, user.getId());
        String refresh = jwtUtil.createJwt("refresh", user.getNickName(), user.getRole(),86400000L, user.getId());

        utilFunction.addRefreshEntity(user.getPlatformId(), refresh);

        response.setHeader("refresh", refresh);
        response.setStatus(HttpStatus.OK.value());

        return access;
    }

    @Transactional
    public void logout(String refreshToken, HttpServletResponse response) {
        if (refreshRepository.existsByRefresh(refreshToken)) {
            refreshRepository.deleteByRefresh(refreshToken);
        }
        response.setHeader("refresh", null);
    }
}
