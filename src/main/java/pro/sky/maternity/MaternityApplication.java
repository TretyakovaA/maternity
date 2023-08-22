package pro.sky.maternity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MaternityApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaternityApplication.class, args);
	}

}
