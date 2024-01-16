package at.spengergasse.cooking.recipes;

import at.spengergasse.cooking.recipes.config.MongoConfig;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import java.util.Arrays;

@Configuration
@Import(MongoConfig.class)
public class MongoTestConfig {

    @Bean
    public MongoClientSettings mongoClientSettings(MongoDBContainer mongoDBContainer) {
        return MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                        builder.hosts(Arrays.asList(new ServerAddress(mongoDBContainer.getHost(), mongoDBContainer.getFirstMappedPort()))))
                .applyConnectionString(new ConnectionString(mongoDBContainer.getReplicaSetUrl()))
                .build();
    }
}

