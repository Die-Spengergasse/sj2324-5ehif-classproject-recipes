package at.spengergasse.cooking.recipes.persistence;

import at.spengergasse.cooking.recipes.domain.Recipe;
import at.spengergasse.cooking.recipes.domain.utils.key.Key;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public class RecipeRepositoryImpl extends SimpleMongoRepository<Recipe, Key> implements RecipeRepository {
    private MongoOperations mongoOperations;
    private MongoEntityInformation entityInformation;

    public RecipeRepositoryImpl(final MongoEntityInformation entityInformation, final MongoOperations mongoOperations){
        super(entityInformation, mongoOperations);

        this.entityInformation = entityInformation;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public List<Recipe> findAllBy(Query query){
        return mongoOperations.find(query, entityInformation.getJavaType(), entityInformation.getCollectionName());
    }
}
