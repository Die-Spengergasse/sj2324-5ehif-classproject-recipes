package at.spengergasse.cooking.recipes.persistance;

import at.spengergasse.cooking.recipes.domain.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
    @Query("{}")
    List<Recipe> findRecipes();

}
