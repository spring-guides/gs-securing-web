package hello;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.EnableWebSecurity;
import org.springframework.security.config.annotation.web.ExpressionUrlAuthorizations;
import org.springframework.security.config.annotation.web.HttpConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpConfiguration http) throws Exception {
        ExpressionUrlAuthorizations expressionUrlAuthorizations = http.authorizeUrls();
        expressionUrlAuthorizations.antMatchers("/hello").hasRole("USER");
        expressionUrlAuthorizations.antMatchers("/**").permitAll();

        http.formLogin().defaultSuccessUrl("/hello");
        http.logout().logoutSuccessUrl("/");
    }

    @Override
    protected void registerAuthentication(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
}
