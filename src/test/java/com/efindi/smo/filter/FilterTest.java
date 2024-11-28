package com.efindi.smo.filter;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.efindi.smo.exception.InvalidODataFormatException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@Slf4j
public class FilterTest {

  public static List<String> validOdataQueryList;

  @BeforeAll
  public static void init() throws IOException {
    validOdataQueryList = IOUtils.readLines(Objects.requireNonNull(ClassLoader
        .getSystemResourceAsStream("valid-odata-queries.txt")), Charset.defaultCharset());
  }

  @Test
  public void criteriaIsNullWhenStringIsEmpty() {
    Throwable throwable = assertThrows(InvalidODataFormatException.class, () -> new Filter(""));
  }

  @Test
  public void validOdataQueriesShouldPassTheTest() throws InvalidODataFormatException {
    for (String validOdataQuery : validOdataQueryList) {
      Filter filter = new Filter(validOdataQuery);
      log.info("{}", filter);
    }
  }

}
