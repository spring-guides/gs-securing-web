package hello.app.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import hello.data.beans.SpringSecurityUser;
import hello.data.values.UserInfo;



// @Service
@Component
public class UserService implements UserDetailsService {

	/**
	 * @inherit
	 */
	@Override
	public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {

		//TODO：実際にはここでDBからユーザ情報を引いてくる必要があるが、一旦ベタ書きで適当にやる。
		
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		String password = enc.encode( "password" );
		String role = "ROLE_USER";
		UserInfo userInfo = new UserInfo( username, password, role );
		
		
		// spring-security フレームワークが処理できる SpringSecurityUser:User:UserDetails を返す。
		return new SpringSecurityUser( userInfo );
	}
}
