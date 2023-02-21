package com.efindi.smo.util;

import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;

public class MongoUtils {

  public static Criteria[] criteriaListToArray(List<Criteria> criteriaList) {
    return criteriaList.toArray(new Criteria[0]);
  }

  public static Criteria create$andCriteria(List<Criteria> criteriaList) {
    return new Criteria().andOperator(criteriaListToArray(criteriaList));
  }

  public static Criteria create$orCriteria(List<Criteria> criteriaList) {
    return new Criteria().orOperator(criteriaListToArray(criteriaList));
  }

}
