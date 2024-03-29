package com.cx.prod.list.config.main;

import org.springframework.boot.web.embedded.jetty.ConfigurableJettyWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
public class MainConfig implements WebMvcConfigurer {

    private static final int MAX_AGE = 3600;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/notFound").setViewName("forward:/index.html");
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableJettyWebServerFactory> containerCustomizer() {
        return container -> container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notFound"));
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20848820);
        return multipartResolver;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/image/**").addResourceLocations("classpath:/static/image/");
        registry.addResourceHandler("/media/**").addResourceLocations("classpath:/static/media/");
        registry.addResourceHandler("/static/js/**").addResourceLocations("classpath:/static/static/js/");
        registry.addResourceHandler("/static/css/**").addResourceLocations("classpath:/static/static/css/");
        registry.addResourceHandler("/static/image/**").addResourceLocations("classpath:/static/static/image/");
        registry.addResourceHandler("/static/media/**").addResourceLocations("classpath:/static/static/media/");
        registry.addResourceHandler("/**/*.jpg").addResourceLocations("classpath:/static/static/media/");
        registry.addResourceHandler("/**/*.jpeg").addResourceLocations("classpath:/static/static/media/");
        registry.addResourceHandler("/**/*.png").addResourceLocations("classpath:/static/static/media/");
        registry.addResourceHandler("/**/*.gif").addResourceLocations("classpath:/static/static/media/");
        registry.addResourceHandler("/**/*.txt").addResourceLocations("classpath:/static/static/txt/");

        // Angular
/*        registry.addResourceHandler("/*.html").addResourceLocations("classpath:/static/webapp/");
        registry.addResourceHandler("/*.js").addResourceLocations("classpath:/static/webapp/");
        registry.addResourceHandler("/*.css").addResourceLocations("classpath:/static/webapp/");
        registry.addResourceHandler("/*.ico").addResourceLocations("classpath:/static/webapp/");
        registry.addResourceHandler("/*.woff").addResourceLocations("classpath:/static/webapp/");
        registry.addResourceHandler("/*.woff2").addResourceLocations("classpath:/static/webapp/");
        registry.addResourceHandler("/*.js.map").addResourceLocations("classpath:/static/webapp/");
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/webapp/assets/");*/
    }

    @Bean
    @Profile("test")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("POST", "OPTIONS", "GET", "DELETE", "PUT")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE);
    }
}