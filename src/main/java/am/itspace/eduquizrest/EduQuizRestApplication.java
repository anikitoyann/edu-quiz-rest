package am.itspace.eduquizrest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@ComponentScan(basePackages = {"am.itspace.eduquizrest", "com.example.eduquizcommon"})
@EntityScan("com.example.eduquizcommon.entity")
@EnableJpaRepositories(basePackages = "com.example.eduquizcommon.repository")
public class EduQuizRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduQuizRestApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
