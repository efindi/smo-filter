package com.efindi.smo.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ODataStringUtils {

    public static final Pattern FILTER_PATTERN = Pattern.compile("([^']\\S*|'.+?')\\s*");

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

    public static List<String> filterPatternSplitToList(String $filter) {
        Matcher m = FILTER_PATTERN.matcher($filter);
        List<String> filterList = new ArrayList();
        while (m.find()) {
            filterList.add(m.group(1));
        }
        return filterList;
    }

}
