package hello;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.AuthenticationRegistry;
import org.springframework.security.config.annotation.web.EnableWebSecurity;
import org.springframework.security.config.annotation.web.ExpressionUrlAuthorizations;
import org.springframework.security.config.annotation.web.HttpConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * @author Josh Long
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String USER_ROLE = "USER";
	private static final String ADMIN_ROLE = "ADMIN";

	@Override
	protected void configure(HttpConfiguration http) throws Exception {

		// cordon off resources by URL
		ExpressionUrlAuthorizations expressionUrlAuthorizations = http.authorizeUrls();

		for (Page securePage : Page.getSecurePages())
			expressionUrlAuthorizations.antMatchers(securePage.getUrl()).hasRole(
					USER_ROLE);

		for (Page insecurePage : Page.getInsecurePages())
			expressionUrlAuthorizations.antMatchers(insecurePage.getUrl()).permitAll();

		// login
		http.formLogin().defaultSuccessUrl(Page.HOME.getUrl()).permitAll();
		// set permitAll for all URLs associated with Form Login

		// logout
		LogoutHandler logoutHandler = new LogoutHandler() {
			@Override
			public void logout(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) {
				if (null != authentication) {
					System.out.println(String.format("logging the user ('%s') out!", ""
							+ authentication.getName()));
				}
			}
		};
		http.logout().logoutSuccessUrl(Page.WELCOME.getUrl())
				.addLogoutHandler(logoutHandler);
	}

	@Override
	protected void registerAuthentication(AuthenticationRegistry registry)
			throws Exception {
		registry.inMemoryAuthentication().withUser("user").password("password")
				.roles(USER_ROLE).and().withUser("admin").password("password")
				.roles(USER_ROLE, ADMIN_ROLE);
	}
}
