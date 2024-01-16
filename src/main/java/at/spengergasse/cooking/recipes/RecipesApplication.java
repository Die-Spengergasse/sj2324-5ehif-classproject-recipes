package at.spengergasse.cooking.recipes;

import at.spengergasse.cooking.recipes.service.user.MockUserData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoRepositories
public class RecipesApplication {

	public static void main(String[] args) {
		MockUserData.startMockUser();
		SpringApplication.run(RecipesApplication.class, args);
	}

}
