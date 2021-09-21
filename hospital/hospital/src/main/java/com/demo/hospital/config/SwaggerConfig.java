package com.demo.hospital.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Value("${app.jwtHeaderString}")
    String accessToken;


    @Bean
    public Docket Api() {
        ArrayList<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(apiKey());
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("demo-api-v1")
                .securitySchemes(apiKeys)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
                .paths(Predicates.not(regex("/error.*")))
                .paths(regex("/demo/v1.*"))
                .build()
//                .apiInfo(getApiInformation());
                .apiInfo(new ApiInfoBuilder()
                        .title("Demo Rest-API")
                        .version("v1")
                        .description("Rest API documentation.")
                        .build());
    }

    @Bean
    public Docket Api2() {
        ArrayList<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(apiKey());
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("demo-api-v2")
                .securitySchemes(apiKeys)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
                .paths(Predicates.not(regex("/error.*")))
                .paths(regex("/demo/v2.*"))
                .build()
//                .apiInfo(getApiInformation());
                .apiInfo(new ApiInfoBuilder()
                        .title("Demo Rest-API")
                        .version("v2")
                        .description("Rest API documentation.")
                        .build());
    }


    private ApiKey apiKey() {
        return new ApiKey("Api Key",accessToken , "header");
    }
}
