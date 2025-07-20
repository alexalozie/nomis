package org.nomisng;

import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.nomisng.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@EnableScheduling
@EnableSwagger2
@SpringBootApplication
@RequiredArgsConstructor
public class NomisApplication {

    @Autowired
    DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(NomisApplication.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new FileSystemResource(ApplicationProperties.modulePath + File.separator + "application.yml"));
        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());
        propertySourcesPlaceholderConfigurer.setIgnoreResourceNotFound(true);
        return propertySourcesPlaceholderConfigurer;
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(NomisApplication.class);
//    }
    /*
     * Provides sensible defaults and convenience methods for configuration.
     * @return a Docket
     */
    @Bean
    public Docket api() {
        final List<ResponseMessage> globalResponses = Arrays.asList(new ResponseMessageBuilder().code(200).message("OK").build(), new ResponseMessageBuilder().code(400).message("Bad Request").build(), new ResponseMessageBuilder().code(500).message("Internal Error").build());
        return new Docket(DocumentationType.SWAGGER_2).securitySchemes(Arrays.asList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name()))).useDefaultResponseMessages(false).globalResponseMessage(RequestMethod.GET, globalResponses).globalResponseMessage(RequestMethod.POST, globalResponses).globalResponseMessage(RequestMethod.DELETE, globalResponses).globalResponseMessage(RequestMethod.PATCH, globalResponses).apiInfo(apiInfo()).securityContexts(Arrays.asList(securityContext())).securitySchemes(Arrays.asList(apiKey())).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }

    /*
     *
     * @return ApiInfo for documentation
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Nomis").description("Nomis Application Api Documentation").license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").termsOfServiceUrl("http://swagger.io/terms/").version("1.0.0").contact(new Contact("Development Team", "http://nomis-ng.org/", "info@nomis-ng.org/demo")).build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    /*
     * @Param name
     * @Param keyName
     * @Param passAs
     * @return ApiKey
     * Sending Authorization:
     */
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
}
