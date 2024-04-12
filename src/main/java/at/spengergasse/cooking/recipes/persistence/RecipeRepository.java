package at.spengergasse.cooking.recipes.persistence;

import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, Key> {

    List<Recipe> findAllBy(Query query);
}
