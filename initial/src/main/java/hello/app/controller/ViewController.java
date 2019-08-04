package hello.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import hello.data.beans.SpringSecurityUser;
import hello.data.values.UserInfo;

@Controller
public class ViewController {
	
	private static final Logger log = LoggerFactory.getLogger( ViewController.class );
	
	
	@RequestMapping("/")
	public String root() {
		
		log.info( "view controller を通して {} にアクセス", "home" );
		return "home";
	}
	
	@RequestMapping("/home")
	public String home() {
		
		log.info( "view controller を通して {} にアクセス", "home" );
		return "home";
	}
	
	@RequestMapping("/login")
	public String login() {
		
		log.info( "view controller を通して {} にアクセス", "login" );
		return "login";
	}
	
	
	@RequestMapping("/hello")
	public String hello( 
			@AuthenticationPrincipal SpringSecurityUser user,
			Model model ) {
		
		final UserInfo info = user.getUserInfo();
		
		log.info( "view controller を通して {} にアクセス", "hello" );
		
		log.info( "ログインユーザ {} さん、ロールは {} です。" 
				, info.userId
				, info.role );
		
		model.addAttribute( "name", info.userId );
		model.addAttribute( "role", info.role );
		
		return "hello";
	}
	
}
