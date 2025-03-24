
package site.nansan.user.service;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.nansan.user.domain.Role;
import site.nansan.user.domain.Users;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final HttpServletRequest request;
    public Users getAuthenticatedUser() {
        String userIdHeader = request.getHeader("X-User-Id");
        String nicknameHeader = request.getHeader("X-User-Nickname");
        String roleHeader = request.getHeader("X-User-Role");
        if (userIdHeader == null || nicknameHeader == null || roleHeader == null) {
            return null;
        }
        return new Users(
                Long.parseLong(userIdHeader),
                nicknameHeader,
                Role.valueOf(roleHeader)  // ENUM 변환
        );

    }

}