package org.example.afarm.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AllConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/boardImages/**")
                .addResourceLocations("file:/C:/Users/user/Desktop/편의점/project_images");
        registry.addResourceHandler("/path/**")
                .addResourceLocations("file:/C:/Users/user/IdeaProjects/afarm/src/main/resources/photo");
    }
}
