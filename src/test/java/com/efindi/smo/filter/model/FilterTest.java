package com.efindi.smo.filter.model;

import com.efindi.smo.exception.InvalidODataFormatException;
import com.efindi.smo.filter.Filter;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

public class FilterTest {

    @Test(expected = InvalidODataFormatException.class)
    public void criteriaIsNullWhenStringIsEmpty() throws InvalidODataFormatException {
        CriteriaDefinition criteriaDefinition = new Filter("").getCriteria();
    }

}
