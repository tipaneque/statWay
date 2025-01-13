package com.statway.utils;

public class Validation {
    public static boolean containsLetter(String text){

        return text.matches(".*[a-zA-Z].*");

    }

    public static boolean isOnlyAlphaNumeric(String text){
        return text.matches(".*[a-zA-Z0-9].*");
    }

    public static boolean isCommaSeparated(String text){

        return text.matches("(-?\\d+(\\.\\d+)?)(,(-?\\d+(\\.\\d+)?))*");

    }

    public static boolean isMoreThanOneComma(String text){

        return text.startsWith(",") || text.endsWith(",") || text.contains(",,");
    }
}
