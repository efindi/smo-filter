package com.efindi.smo.filter.model;

import com.efindi.smo.exception.InvalidODataFormatException;
import com.efindi.smo.filter.Filter;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilterTest {

    public static List<String> validOdataQueryList;

    @BeforeAll
    public static void init() throws IOException {
        validOdataQueryList = IOUtils.readLines(ClassLoader.getSystemResourceAsStream("valid-odata-queries.txt"), "UTF-8");
    }

    @Test
    public void criteriaIsNullWhenStringIsEmpty() {
        Throwable throwable = assertThrows(InvalidODataFormatException.class, () -> new Filter(""));
    }

    @Test
    public void validOdataQueriesShouldPassTheTest() throws InvalidODataFormatException {
        for (String validOdataQuery : validOdataQueryList) {
            new Filter(validOdataQuery);
        }
    }

}
