package hello;

import org.springframework.autoconfigure.EnableAutoConfiguration;
import org.springframework.bootstrap.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
@ComponentScan
public class Application {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }

}
