package hello.data.beans;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import hello.data.values.UserInfo;

// SpringSecurityUser extends User implements UserDetails
public class SpringSecurityUser extends User {
	
	/** default serial version uid. */
	private static final long serialVersionUID = 1L;
	
	private final UserInfo userInfo;

	public SpringSecurityUser( UserInfo userInfo ) {
		super( userInfo.userId, 
				userInfo.password,
				AuthorityUtils.createAuthorityList( userInfo.role ) );
		
		this.userInfo = userInfo;
	}
	
	public UserInfo getUserInfo() {
		return this.userInfo;
	}
}
