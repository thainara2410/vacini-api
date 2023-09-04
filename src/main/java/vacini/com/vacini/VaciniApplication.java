package vacini.com.vacini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VaciniApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaciniApplication.class, args);
	}

}
