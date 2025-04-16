package FAQ.Faq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
public class FaqApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaqApplication.class, args);
	}

}
