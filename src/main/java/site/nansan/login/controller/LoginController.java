package site.nansan.login.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import site.nansan.login.dto.JoinRequestDto;
import site.nansan.login.dto.RoleSelectionDto;
import site.nansan.login.service.LoginService;
import site.nansan.user.domain.Role;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController implements LoginSwaggerController{

    private final LoginService loginService;

    @Override
    public ResponseEntity<Map<String, String>> createOrGetUser(JoinRequestDto joinDTO, HttpServletResponse response) {
        String accessToken = loginService.joinOrGetUser(joinDTO, response);

        // Todo : status 필드를 추가하고, 회원가입할때는 0을 보내 추가 정보를 보내도록 하고 기존 로그인때는 1을 보내서 넘어간다. response body에 status를 추가한다.

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

    @PostMapping("/user/role")
    public ResponseEntity<Void> selectUserRole(@RequestBody RoleSelectionDto roleRequest) {
        Long userId = roleRequest.getUserId();
        String roleString = roleRequest.getRole();

        if (!Role.isValidRole(roleString)) {
            return ResponseEntity.badRequest().build();
        }

        Role role = Role.valueOf(roleString);
        return loginService.selectUserRole(userId, role);
    }



}
