package com.tavi.duongnt.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration
public class TilesConfig implements WebMvcConfigurer {
    @Bean(name = "viewResolver")
    public ViewResolver getViewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();

        // TilesView 3
        viewResolver.setViewClass(TilesView.class);

        return viewResolver;
    }

    @Bean(name = "tilesConfigurer")
    public TilesConfigurer getTilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();

        // TilesView 3
        tilesConfigurer.setDefinitions("/WEB-INF/tiles.xml");

        return tilesConfigurer;
    }


    @Override //cau hinh duong dan tinh
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/resources/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/resources/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/resources/img/**")
                .addResourceLocations("classpath:/static/img/");
    }
}
