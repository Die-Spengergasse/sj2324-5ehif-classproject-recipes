package at.spengergasse.cooking.recipes.domain.utils;

import at.spengergasse.cooking.recipes.domain.Unit;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

public class UnitConverter {

    @WritingConverter
    public static class UnitToStringConverter implements Converter<Unit, String> {
        @Override
        public String convert(Unit unit) {
            switch (unit) {
                case GRAMS:
                    return "g";
                case MILLILITER:
                    return "ml";
                case TABLESPOONS:
                    return "tbs";
                case TEASPOONS:
                    return "tsp";
                case PACK:
                    return "pa";
                case PINCH:
                    return "pi";
                default:
                    throw new IllegalArgumentException(unit + " is not a valid Unit!");
            }
        }
    }

    @ReadingConverter
    public static class StringToUnitConverter implements Converter<String, Unit> {
        @Override
        public Unit convert(String s) {
            switch (s) {
                case "g":
                    return Unit.GRAMS;
                case "ml":
                    return Unit.MILLILITER;
                case "tbs":
                    return Unit.TABLESPOONS;
                case "tsp":
                    return Unit.TEASPOONS;
                case "pa":
                    return Unit.PACK;
                case "pi":
                    return Unit.PINCH;
                default:
                    throw new IllegalArgumentException(s + " is not a valid Unit!");
            }
        }
    }
}
