package hello.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String hello( Model model ) {
		
		log.info( "view controller を通して {} にアクセス", "hello" );
		return "hello";
	}
	
}
