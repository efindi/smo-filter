package com.efindi.smo.util;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

public class DateUtils {

  public static Date ISO8601DateTimeParser(String str) {
    return StringUtils.contains(str, "+") ?
        Date.from(OffsetDateTime.parse(str).toInstant()) :
        Date.from(Instant.parse(str));
  }

}
