package com.efindi.model;

import org.springframework.data.mongodb.core.query.Criteria;

public class Filter {

    private String $filter;
    private Criteria criteria;

    public Filter() {
    }

    public Filter(String $filter) {
        this.$filter = $filter;
    }

    public String get$filter() {
        return $filter;
    }

    public void set$filter(String $filter) {
        this.$filter = $filter;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "$filter='" + $filter + '\'' +
                ", criteria=" + criteria +
                '}';
    }
}
