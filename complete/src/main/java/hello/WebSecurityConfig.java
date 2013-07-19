package hello;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity> expressionUrlAuthorizations = http.authorizeUrls();
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