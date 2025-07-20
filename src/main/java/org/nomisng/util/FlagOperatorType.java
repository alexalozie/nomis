package org.nomisng.util;

public enum FlagOperatorType {
    EQUAL_T0("equal_to"),
    GREATER_THAN("greater_than"),
    LESS_THAN("less_than"),
    GREATER_THAN_OR_EQUAL_TO("greater_than_or_equal_to"),
    FORM_LEVEL("form_level"),
    LESS_THAN_OR_EQUAL_TO("less_than_or_equal_to"),
    INVALID("invalid");

    String val;
    FlagOperatorType(String val) {
        this.val = val;
    }

    public static FlagOperatorType from(String text) {
        for (FlagOperatorType output : values()) {
            if(output.val.equalsIgnoreCase(text)) return output;
        }
        return INVALID;
    }
}
