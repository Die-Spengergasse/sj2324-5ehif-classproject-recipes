package at.spengergasse.cooking.recipes.domain.utils;

import java.util.Optional;
import java.util.function.Function;
import jakarta.persistence.*;

public class AbstractEnumToStringConverter <E extends Enum> implements AttributeConverter<E, String> {

    private final Function<E, String> toDbValue;
    private final Function<String, E> fromDbValue;


    protected AbstractEnumToStringConverter(Function<E, String> toDbValue, Function<String, E> fromDbValue){
        this.toDbValue = toDbValue;
        this.fromDbValue = fromDbValue;
    }
    @Override
    public String convertToDatabaseColumn(E e) {
        return Optional.ofNullable(e).map(toDbValue).orElse(null);
    }

    @Override
    public E convertToEntityAttribute(String s) {
        return Optional.ofNullable(s).map(fromDbValue).orElse(null);
    }
}
