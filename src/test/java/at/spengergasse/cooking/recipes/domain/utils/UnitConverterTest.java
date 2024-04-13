package at.spengergasse.cooking.recipes.domain.utils;

import at.spengergasse.cooking.recipes.domain.Unit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
/*
class UnitConverterTest {
    private UnitConverter unitConverter = new UnitConverter();


    @Test
    void ensureCorrectHandlingOfNullValues(){
        assertThat(unitConverter.convertToDatabaseColumn(null)).isNull();
        assertThat(unitConverter.convertToEntityAttribute(null)).isNull();
    }

    @Test
    void ensureCorrectHandlingOfFalseValues() {
        String dbValue = "ABC";
        try{
            Unit unit = unitConverter.convertToEntityAttribute(dbValue);
        }catch(IllegalArgumentException ex){
            assertThat(ex.getMessage()).contains("ABC is not a valid Unit");
        }
    }

    @ParameterizedTest
    @MethodSource
    void ensureCorrectHandlingOfGivenValues(Unit javaValue, String dbValue){
        assertEquals(dbValue, unitConverter.convertToDatabaseColumn(javaValue));
        assertEquals(javaValue, unitConverter.convertToEntityAttribute(dbValue));
    }

    static Stream<Arguments> ensureCorrectHandlingOfGivenValues(){
        return Stream.of(
                Arguments.of(Unit.GRAMS, "g"),
                Arguments.of(Unit.MILLILITER, "ml"),
                Arguments.of(Unit.TABLESPOONS, "tbs"),
                Arguments.of(Unit.TEASPOONS ,"tsp"),
                Arguments.of(Unit.PACK ,"pa"),
                Arguments.of(Unit.PINCH , "pi")
        );
    }
}*/