package site.nansan.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI userServiceOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Nansan User Service API")
                                .description("유저 관련 기능 API")
                                .version("v1")
                );
    }
}
