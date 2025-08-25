package vn.edu.eiu.testlab.fecse456studentiddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FeCse456StudentIdDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeCse456StudentIdDemoApplication.class, args);
    }

}
