package site.nansan.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import site.nansan.login.dto.JoinRequestDto;

import java.util.Map;

@Tag(name = "Join/login API", description = "회원가입/로그인 API입니다.")
public interface LoginSwaggerController {

    @Operation(summary = "회원 가입/로그인", description = "회원 가입/로그인.")
    @ApiResponse(responseCode = "201", description = "회원 가입/로그인에 성공하였습니다.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "회원 가입/로그인 요청 데이터",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = JoinRequestDto.class),
                    examples = @ExampleObject(
                            name = "회원 가입/로그인 예제",
                            value = "{ \"socialPlatform\": \"kakao\", \"email\": \"example@naver.com\", \"platformId\": \"kakao-123456789\", \"nickName\": \"전성호\" }"
                    )
            )
    )
    @PostMapping("/api/v1/login")
    ResponseEntity<Map<String, String>> createOrGetUser(
            @RequestBody @Valid JoinRequestDto joinDTO,
            HttpServletResponse response
    );

    @Operation(summary = "로그아웃", description = "사용자를 로그아웃 처리합니다.")
    @ApiResponse(responseCode = "200", description = "로그아웃에 성공하였습니다.")
    @PostMapping("/api/v1/logout")
    ResponseEntity<Void> logout(
            @RequestHeader("refresh") String refreshToken,
            HttpServletResponse response
    );
}
