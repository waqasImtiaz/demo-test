package com.demo.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

  public static final String TITLE = "Test-API";
  public static final String PROJECT_DESCRIPTION = "Project-description";

  @Value("${project.description}")
  private String projectDescription;

  @Value("${api.description}")
  private String apiDescription;

  @Value("${api.version}")
  private String apiVersion;

  @Bean
  public Docket testApi() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
        .build()
        .tags(new Tag(PROJECT_DESCRIPTION, projectDescription))
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title(TITLE).description(apiDescription).version(apiVersion).build();
  }

}
