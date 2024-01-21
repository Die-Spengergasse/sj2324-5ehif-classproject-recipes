package at.spengergasse.cooking.recipes.domain.utils.search;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FilterOperation {
    EQUAL("eq"),
    NOT_EQUAL("neq"),
    CONTAINS("con");

    private final String value;

    FilterOperation(String value){
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString(){
        return String.valueOf(value);
    }

    public static FilterOperation fromValue(String value) {
        for (FilterOperation op : FilterOperation.values()) {
            if (String.valueOf(op.value).equalsIgnoreCase(value)) {
                return op;
            }
        }
        return null;
    }
}
