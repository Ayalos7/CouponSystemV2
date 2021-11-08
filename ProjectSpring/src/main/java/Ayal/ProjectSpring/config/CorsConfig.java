package Ayal.ProjectSpring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Collections;


@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        //create new url configuration for browsers
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //create new cors configuration....
        CorsConfiguration config = new CorsConfiguration();
        //allow to get credentials in cors
        config.setAllowCredentials(true);
        //allow to get from any ip/domain
        config.setAllowedOriginPatterns(Collections.singletonList("*"));        //allow to get any header
        config.addAllowedHeader("*");
        config.addExposedHeader("*");
        //allow to get methods
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        //allow to get any route -> localhost:8080/api/lecturer -> /api/lecture is route
        source.registerCorsConfiguration("/**", config);
        //return new configuration
        return new CorsFilter(source);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}


