
package site.nansan.user.service;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.nansan.user.domain.Users;
import site.nansan.user.dto.UserDto;
import site.nansan.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final HttpServletRequest request;

    public Users getAuthenticatedUser() {

        String userIdHeader = request.getHeader("X-User-Id");

        if (userIdHeader == null) {
            return null;
        }

        try {
            Long userId = Long.parseLong(userIdHeader);
            return userRepository.findById(userId).orElse(null); // DB에서 전체 정보 조회
        } catch (NumberFormatException e) {
            log.warn("잘못된 userIdHeader: {}", userIdHeader);
            return null;
        }
    }

    public UserDto getAuthenticatedUser(Long userId) {

        return UserDto.from(userRepository.findById(userId).orElseThrow());
    }

    private final UserRepository userRepository;

    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

}