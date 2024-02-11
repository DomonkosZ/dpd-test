package hu.domi.dpd.dpdbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The {@code CorsConfig} class is responsible for configuring Cross-Origin Resource Sharing (CORS) for the application.
 * It extends {@link WebMvcConfigurer} interface to override the necessary methods for configuring CORS.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }

}