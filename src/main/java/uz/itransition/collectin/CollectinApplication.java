package uz.itransition.collectin;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import uz.itransition.collectin.config.security.AppProperties;
import uz.itransition.collectin.config.security.OpenApiProperties;

@OpenAPIDefinition
@SpringBootApplication
@EnableConfigurationProperties({
        AppProperties.class,
        OpenApiProperties.class
})
public class CollectinApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollectinApplication.class, args);
    }

}
