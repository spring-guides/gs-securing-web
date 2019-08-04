package hello.app.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
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
	
	@SuppressWarnings("serial")
	private final HashMap<String,String> ROLE_TABLE = new HashMap<String, String>() {
		{ 
			//  ※こっちは ROLE_ プリフィックス付けてないとダメらしい。
			put( "admin", "ROLE_ADMIN" );         // システム管理者、最強。
			put( "user1", "ROLE_COMPANY" );       // 企業ユーザ
			put( "user2", "ROLE_SHOP" );          // 店舗ユーザ
			put( "user3", "ROLE_HOLE_STAFF" );    // ホールスタッフ
			put( "user4", "ROLE_KITCHIN_STAFF" ); // キッチンスタッフ
			put( "sysop", "ROLE_OPERATOR" );      // 運用者
		}
	};
	
	
	private static final Logger log = LoggerFactory.getLogger( UserService.class );

	
	/**
	 * @inherit
	 */
	@Override
	public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
		
		// TODO：実際にはここでDBからユーザ情報を引いてくる必要があるが、一旦ベタ書きで適当にやる。
		
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		String password = enc.encode( "password" );
		String role = ROLE_TABLE.get( username );
		if ( null == role ) role = "ROLE_GUEST";
		
		log.info( "{} - {}", username, role );
		
		UserInfo userInfo = new UserInfo( username, password, role );
		
		
		// spring-security フレームワークが処理できる SpringSecurityUser:User:UserDetails を返す。
		
		UserDetails ud = new SpringSecurityUser( userInfo );
		var auths = ud.getAuthorities();
		for ( GrantedAuthority auth : auths ) {
			
			String a = auth.getAuthority();
			log.info( a );
		}
		
		return ud;
	}
}
