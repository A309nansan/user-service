package site.nansan.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.nansan.user.domain.Users;
import site.nansan.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController implements UserSwaggerController {

    private final UserService userService;

    @Override
    public ResponseEntity<Users> getAuthenticatedUser() {
        Users authenticatedUser = userService.getAuthenticatedUser();

        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(authenticatedUser);
    }
}
