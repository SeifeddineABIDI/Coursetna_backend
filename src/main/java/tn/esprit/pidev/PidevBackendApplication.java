package tn.esprit.pidev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PidevBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PidevBackendApplication.class, args);
	}

}
