package site.nansan.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info().title("Nansan API 명세서")
                .description("<h3>Nansan API Reference for Developers</h3>Swagger를 이용한 API<br>")
                .version("v1");

        // Authorization 헤더에 JWT 토큰을 넣을 수 있도록 설정
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .description("JWT Access Token (예: Bearer your_token)");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Authorization");

        return new OpenAPI()
                .servers(List.of(
                        new Server().url("https://i12a402.p.ssafy.io").description("Production Server"),
                        new Server().url("http://i12a402.p.ssafy.io:8080").description("Production Server"),
                        new Server().url("http://localhost:8080/").description("Local Server"),
                        new Server().url("http://i12a402.p.ssafy.io").description("Production Server")
                ))
                .components(new Components().addSecuritySchemes("Authorization", securityScheme))
                .addSecurityItem(securityRequirement)
                .info(info);
    }
}
