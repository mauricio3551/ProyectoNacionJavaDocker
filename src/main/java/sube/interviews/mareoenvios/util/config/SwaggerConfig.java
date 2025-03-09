package sube.interviews.mareoenvios.util.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mareo-envios")
                        .version("1.0")
                        .description("Web Service &quot;Mareo envíos&quot; para entrevista SUBE"));
    }

    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .pathsToExclude("/error", "/actuator/**")
                .build();
    }
}
