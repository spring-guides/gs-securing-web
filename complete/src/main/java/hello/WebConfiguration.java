package hello;

import org.springframework.bootstrap.context.annotation.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

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
}
