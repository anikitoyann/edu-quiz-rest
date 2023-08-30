package am.itspace.eduquizrest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = {"am.itspace.eduquizrest", "com.example.eduquizcommon"})
@EntityScan("com.example.eduquizcommon.entity")
@EnableJpaRepositories(basePackages = "com.example.eduquizcommon.repository")
public class EduQuizRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduQuizRestApplication.class, args);
	}
}
