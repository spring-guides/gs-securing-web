package hello;

import java.util.Collection;

import org.springframework.bootstrap.SpringApplication;
import org.springframework.bootstrap.context.annotation.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * @author Josh Long
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan
@EnableWebMvc
@Import(WebSecurityConfiguration.class)
public class Application extends WebMvcConfigurerAdapter {

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
	public SpringTemplateEngine templateEngine(
			Collection<ITemplateResolver> templateResolvers) {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		for (ITemplateResolver templateResolver : templateResolvers) {
			engine.addTemplateResolver(templateResolver);
		}
		engine.addDialect(new SpringSecurityDialect());
		return engine;
	}

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(Application.class, args);
	}

}
