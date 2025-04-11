package site.nansan.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import site.nansan.user.domain.Users;
import site.nansan.user.dto.UserDto;

public interface  UserSwaggerController {

    @Operation(summary = "Get Authenticated User", description = "인증된 사용자 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 인증된 사용자 정보 반환"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @GetMapping("/me")
    ResponseEntity<Users> getAuthenticatedUser();

    @Operation(summary = "Get Authenticated User Information", description = "인증된 사용자 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 인증된 사용자 정보 반환"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @GetMapping("/passport/{userId}")
    ResponseEntity<UserDto> getAuthenticatedUser(@PathVariable Long userId);

    // 아래부터 추가한 코드
    @Operation(summary = "Generate Teacher Code", description = "선생님 역할의 사용자를 위한 임시 코드를 생성합니다. 유효기간은 24시간입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 코드 생성"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (선생님이 아님)")
    })
    @PostMapping("/teacher/code")
    ResponseEntity<?> generateTeacherCode();

    @Operation(summary = "Get Teacher Code", description = "선생님 역할의 사용자에게 할당된 임시 코드를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 코드 조회"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403", description = "권한 없음 (선생님이 아님)"),
            @ApiResponse(responseCode = "404", description = "코드 없음")
    })
    @GetMapping("/teacher/code")
    ResponseEntity<?> getTeacherCode();
}
