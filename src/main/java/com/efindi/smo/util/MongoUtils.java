package com.efindi.smo.util;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public class MongoUtils {

    public static Criteria[] criteriaListToArray(List<Criteria> criteriaList) {
        return criteriaList.toArray(new Criteria[criteriaList.size()]);
    }

    public static Criteria create$andCriteria(List<Criteria> criteriaList) {
        return new Criteria().andOperator(criteriaListToArray(criteriaList));
    }

    public static Criteria create$orCriteria(List<Criteria> criteriaList) {
        return new Criteria().orOperator(criteriaListToArray(criteriaList));
    }

}
