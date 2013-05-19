package hello;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.AuthenticationRegistry;
import org.springframework.security.config.annotation.web.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.*;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String USER_ROLE = "USER";
    private static final String ADMIN_ROLE = "ADMIN";

    @Override
    public void configure(org.springframework.security.config.annotation.web.WebSecurityConfiguration builder) throws Exception {
        builder.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpConfigurator http) throws Exception {

        // cordon off resources by URL
        ExpressionUrlAuthorizations expressionUrlAuthorizations = http.authorizeUrls();

        for (String securePage : Pages.getSecurePages())
            expressionUrlAuthorizations.antMatchers("/" + securePage).hasRole(USER_ROLE);

        for (String insecurePage : Pages.getInsecurePages())
            expressionUrlAuthorizations.antMatchers("/" + insecurePage).permitAll();

        // login
        http.formLogin()
                .defaultSuccessUrl("/" + Pages.HOME)
                .permitAll();  // set permitAll for all URLs associated with Form Login

        // logout
        LogoutHandler logoutHandler = new LogoutHandler() {
            @Override
            public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                if (null != authentication)
                    System.out.println(String.format("logging the user ('%s') out!", "" + authentication.getName()));
            }
        };
        http.logout()
                .logoutSuccessUrl("/" + Pages.WELCOME)
                .addLogoutHandler(logoutHandler);
    }

    @Override
    protected void registerAuthentication(AuthenticationRegistry registry) throws Exception {
        registry.inMemoryAuthentication()
                .withUser("user").password("password").roles(USER_ROLE).and()
                .withUser("admin").password("password").roles(USER_ROLE, ADMIN_ROLE);
    }
}
