package hello;

import org.springframework.bootstrap.context.annotation.*;
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
    private Collection<String> pagesCollection = new ArrayList<String>();

    public WebConfiguration() {
        Collections.addAll(this.pagesCollection, Pages.getInsecurePages());
        Collections.addAll(this.pagesCollection, Pages.getSecurePages());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        for (String p : this.pagesCollection) {
            registry.addViewController("/" + p).setViewName(p);
        }
        registry.addViewController("/").setViewName(Pages.WELCOME);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer c) {
        c.enable();
    }

    @ConditionalOnMissingBean(SpringTemplateEngine.class)
    @Configuration
    public static class ThymeleafConfiguration {

        @Bean
        public SpringTemplateEngine templateEngine(Collection<ITemplateResolver> templateResolvers) {
            SpringTemplateEngine engine = new SpringTemplateEngine();
            for (ITemplateResolver templateResolver : templateResolvers) {
                engine.addTemplateResolver(templateResolver);
            }
            // we need this custom SpringTemplateEngine specifically to add support for Spring Security
            engine.addDialect(new SpringSecurityDialect());
            return engine;
        }
    }
}
