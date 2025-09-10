package com.example.happymop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // Serve static assets (HTML/CSS/JS/images) from the external frontend 'src' folder.
    // This maps typical static file patterns and avoids catching API routes like /api/**.
    String frontendPath = "file:///C:/Users/gurumurthy/tataproject/happymopf/src/";

    // Use a single catch-all pattern for static assets; PathPatternParser does not accept patterns
    // like '/**/*.html' after a '**' element. Serve files from the frontend folder.
    registry.addResourceHandler("/index.html", "/**")
        .addResourceLocations(frontendPath)
        .setCachePeriod(0);
    }
}
