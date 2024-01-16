package at.spengergasse.cooking.recipes.persistance;

import at.spengergasse.cooking.recipes.domain.NutrientSummary;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NutrientRepository extends MongoRepository<NutrientSummary, ObjectId> {
    @Query("{}")
    List<NutrientSummary> findNutrients();
}
