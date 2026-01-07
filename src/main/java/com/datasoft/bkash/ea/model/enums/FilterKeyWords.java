package com.datasoft.bkash.ea.model.enums;

public enum FilterKeyWords {
    WHERE(""),
    AND("AND"),
    OR("OR"),
    IS("="),
    IS_NOT("<>"),
    IS_BEFORE("<"),
    IS_AFTER(">"),
    IS_ON_OR_BEFORE("<="),
    IS_ON_OR_AFTER(">="),
    IS_EQUAL("="),
    IS_NOT_EQUAL("<>"),
    IS_GREATER_THAN(">"),
    IS_LESS_THAN("<"),         // Added IS_LESS_THAN
    IS_GREATER_EQUAL_THAN(">="), // Added IS_GREATER_EQUAL_THAN
    IS_LESSER_EQUAL_THAN("<=");


    public final String label;

    public String getValue(){
        return this.label;
    }

    FilterKeyWords(String label) {
        this.label = label;
    }


}
