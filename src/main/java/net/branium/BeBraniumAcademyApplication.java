package net.branium;

import net.branium.constants.ApplicationConstants;
import net.branium.services.impl.AuthenticationServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BeBraniumAcademyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeBraniumAcademyApplication.class, args);
    }

}
