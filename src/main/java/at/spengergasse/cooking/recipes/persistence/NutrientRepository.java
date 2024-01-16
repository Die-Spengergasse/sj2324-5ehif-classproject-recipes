package at.spengergasse.cooking.recipes.persistence;

import at.spengergasse.cooking.recipes.domain.NutrientSummary;
import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NutrientRepository extends MongoRepository<NutrientSummary, Key> {
    @Query("{}")
    List<NutrientSummary> findNutrients();
}
