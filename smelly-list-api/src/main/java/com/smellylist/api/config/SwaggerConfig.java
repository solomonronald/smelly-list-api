package com.smellylist.api.config;

import com.smellylist.api.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Spring Configuration for Swagger
 */
// Make sure Swagger is only available in dev profile
@Profile(Constants.PROFILE_DEV)
@Configuration
public class SwaggerConfig {

    private final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    @Value("${server.port}")
    private int serverPort;

    @Value("${server.servlet.contextPath}")
    private String contextPath;

    @Bean
    public Docket productApi() {

        String swaggerUiUrl = "http://localhost:" + serverPort + contextPath + "/swagger-ui/index.html";
        logger.info("Swagger UI available at " + swaggerUiUrl);

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.smellylist.api"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Smelly List API")
                .description("API Documentation for Smelly List")
                .licenseUrl("https://github.com/solomonronald/smelly-list-api/blob/main/LICENSE")
                .contact(new Contact("Solomon Ronald", "http://solomonronald.com", "github@solomonronald.com"))
                .build();
    }
}
