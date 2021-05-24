package cdp.d2.m2.event.rest.configuration;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

//@Configuration
//@EnableSwagger2
public class SpringFoxConfiguration {

//    @Primary
//    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }

//    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cdp.d2.m2.event.rest"))
                .paths(PathSelectors.ant("/api"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
//        Contact con = new Contact("Uladzislau Hritsau", "https://uladzislau-hrytsau.com", "uladzislau.hrytsau@gmail.com");
        return new ApiInfo(
                "Events : Spring Boot REST API",
                "Spring Boot REST API for events management",
                "1.0",
                "Terms of service",
                "Uladzislau Hritsau https://uladzislau-hrytsau.com uladzislau.hrytsau@gmail.com",
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
    }


}
