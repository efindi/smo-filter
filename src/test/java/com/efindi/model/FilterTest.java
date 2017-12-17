package com.efindi.model;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

public class FilterTest {

    @Test
    public void sanity() {
        Assert.assertEquals(2, 1 + 1);
    }

    @Test
    public void criteriaIsNullWhenStringIsEmpty() {
        CriteriaDefinition criteriaDefinition = new Filter("").getCriteriaDefinition();
        Assert.assertNull(criteriaDefinition);
    }
}
