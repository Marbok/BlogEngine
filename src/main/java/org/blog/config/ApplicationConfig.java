package org.blog.config;

import org.blog.dao.ArticleDao;
import org.blog.listeners.StartEventContextListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = "org.blog")
@Import(HibernateConfig.class)
@EnableWebMvc
public class ApplicationConfig implements WebMvcConfigurer {

    @Bean
    public ViewResolver resolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("");
        resolver.setSuffix(".html");
        return resolver;
    }

//    @Bean
//    @Autowired
//    public StartEventContextListener startEventContextListener(ArticleDao dao) {
//        return new StartEventContextListener(dao);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
    }


}
