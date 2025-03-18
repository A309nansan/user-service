package site.nansan.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import site.nansan.user.domain.Users;

public interface UserSwaggerController {

    @Operation(summary = "Get Authenticated User", description = "인증된 사용자 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 인증된 사용자 정보 반환"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @GetMapping("/api/v1/auth/user/me")
    ResponseEntity<Users> getAuthenticatedUser();
}
