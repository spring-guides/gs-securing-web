package hello;

import org.springframework.bootstrap.context.annotation.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.*;

/**
 * @author Josh Long
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan
@EnableWebMvc
@Import(WebSecurityConfiguration.class)
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        for (Page p : Page.getInsecurePages()) {
            registry.addViewController(p.getUrl()).setViewName(p.getView());
        }
        for (Page p : Page.getSecurePages()) {
            registry.addViewController(p.getUrl()).setViewName(p.getView());
        }
    }

    @Bean
    public SpringTemplateEngine templateEngine(Collection<ITemplateResolver> templateResolvers) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        for (ITemplateResolver templateResolver : templateResolvers) {
            engine.addTemplateResolver(templateResolver);
        }
        engine.addDialect(new SpringSecurityDialect());
        return engine;
    }

}
