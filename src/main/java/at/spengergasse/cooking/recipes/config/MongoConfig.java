package at.spengergasse.cooking.recipes.config;

import at.spengergasse.cooking.recipes.domain.utils.UnitConverter;
import at.spengergasse.cooking.recipes.domain.utils.ZonedDateTimeReadConverter;
import at.spengergasse.cooking.recipes.domain.utils.ZonedDateTimeWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new UnitConverter.UnitToStringConverter(),
                new UnitConverter.StringToUnitConverter(),
                new ZonedDateTimeReadConverter(),
                new ZonedDateTimeWriteConverter()
        ));
    }
}
