package indiana.indi.indiana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class IndianaApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndianaApplication.class, args);
    }

}
