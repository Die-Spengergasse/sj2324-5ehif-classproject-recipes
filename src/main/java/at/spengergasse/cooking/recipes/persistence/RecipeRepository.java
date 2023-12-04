package at.spengergasse.cooking.recipes.persistence;

import at.spengergasse.cooking.recipes.domain.Recipe;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RecipeRepository extends MongoRepository<Recipe, ObjectId> {
    @Query("{}")
    List<Recipe> findRecipes();

    Recipe findRecipeByKey(String key);
    void deleteRecipeByKey(String key);

}
