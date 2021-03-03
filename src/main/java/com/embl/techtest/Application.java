package com.embl.techtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2
public class Application {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(securityContext())
                .securitySchemes(apiKey())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.embl.techtest"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                "Ravi Joshi",
                "https://www.linkedin.com/in/joshiravi9/",
                "ravihj5@gmail.com");

        List<VendorExtension> vext = new ArrayList<>();
        return new ApiInfo(
                "Persons API",
                "API for CRUD Operations for Persons",
                "0.0.1",
                "https://www.ebi.ac.uk/about",
                contact,
                "Test for EMBL EBI",
                "https://www.ebi.ac.uk/about",
                vext);
    }

    private List<SecurityScheme> apiKey() {
        List<SecurityScheme> keys = new ArrayList<>();
        keys.add(new ApiKey("JWT", AUTHORIZATION_HEADER, "header"));

        return keys;
    }

    private List<SecurityContext> securityContext() {
        SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(regex(DEFAULT_INCLUDE_PATTERN))
                .build();

        List<SecurityContext> contexts = new ArrayList<>();
        contexts.add(securityContext);

        return contexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        List<SecurityReference> list = new ArrayList<>();
        list.add(new SecurityReference("JWT", authorizationScopes));

        return list;
    }
}
