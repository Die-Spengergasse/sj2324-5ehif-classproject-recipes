package at.spengergasse.cooking.recipes.domain.utils;

import at.spengergasse.cooking.recipes.domain.Unit;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UnitConverter extends AbstractEnumToStringConverter<Unit>{

    public UnitConverter(){
        super((o) -> switch(o){
            case GRAMS -> "g";
            case MILLILITER -> "ml";
            case TABLESPOONS -> "tbs";
            case TEASPOONS -> "tsp";
            case PACK -> "pa";
            case PINCH -> "pi";
        }, (v) -> switch (v){
            case "g" -> Unit.GRAMS;
            case "ml" -> Unit.MILLILITER;
            case "tbs" -> Unit.TABLESPOONS;
            case "tsp" -> Unit.TEASPOONS;
            case "pa" -> Unit.PACK;
            case "pi" -> Unit.PINCH;
            default -> throw new IllegalArgumentException("%s is not a valid Unit!".formatted(v));
        });

    }
}
