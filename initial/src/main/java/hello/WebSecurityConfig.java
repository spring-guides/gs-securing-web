package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import hello.app.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /** @inherit */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                
            .and().formLogin()
                .loginPage("/login")
                .usernameParameter( "username" )
                .passwordParameter( "password" )
                .permitAll()
                
            .and().logout()
                .permitAll();
    }

    
// インメモリのユーザ管理（公式チュートリアル）実装から、
// 独自のユーザ管理実装に変更。
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//    	
//		UserDetails user = User.withDefaultPasswordEncoder()
//				.username( "admin" )
//				.password( "admin" )
//				.roles( "USER" )
//				.build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
    
    @Configuration
    protected static class AuthenticationConfiguration
    extends GlobalAuthenticationConfigurerAdapter {
    	
        /** 認証ユーザ情報を取得するサービス */
        @Autowired
        UserService service;

        /** @inherit */
        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
			
			auth.userDetailsService( service )
				// パスワードの暗号化方式を設定する場合は以下。
				// ※ デフォルトだと平文になる。
				.passwordEncoder( new BCryptPasswordEncoder() )
			;
			 
        }
    }
}