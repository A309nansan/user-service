package site.nansan.login.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import site.nansan.login.dto.JoinRequestDto;
import site.nansan.login.service.LoginService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController implements LoginSwaggerController{

    private final LoginService loginService;

    @Override
    public ResponseEntity<Map<String, String>> createOrGetUser(JoinRequestDto joinDTO, HttpServletResponse response) {
        String accessToken = loginService.joinOrGetUser(joinDTO, response);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("access", accessToken);

        return ResponseEntity.ok(responseBody);
    }

    @Override
    public ResponseEntity<Void> logout(@RequestHeader("refresh") String refreshToken, HttpServletResponse response) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        loginService.logout(refreshToken, response);
        return ResponseEntity.ok().build();
    }

}
