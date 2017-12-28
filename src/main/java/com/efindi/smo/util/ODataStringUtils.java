package com.efindi.smo.util;

import org.apache.commons.lang3.StringUtils;

public class ODataStringUtils {

    public static String extractDatetime(String value) {
        String[] searchList = {"datetime", "'"}, replaceList = {"", ""};
        return StringUtils.replaceEachRepeatedly(value, searchList, replaceList);
    }

    public static String extractString(String value) {
        String[] searchList = {"'"}, replaceList = {""};
        return StringUtils.replaceEachRepeatedly(value, searchList, replaceList);
    }

    public static boolean isSingleQuoted(String value) {
        return value.startsWith("'") && value.endsWith("'");
    }


    public static boolean isBoolean(String value) {
        return Boolean.TRUE.toString().equals(value) || Boolean.FALSE.toString().equals(value);
    }

}
