package hello;

import org.springframework.bootstrap.SpringApplication;

/**
 * @author Josh Long
 */
public class Main {
     public static void main(String[] args) throws Throwable {
        SpringApplication.run(WebConfiguration.class, args );
    }
}
