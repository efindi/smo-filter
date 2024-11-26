# Spring MongoDB OData $filter
OData Version 4.01 $filter parser for Spring Data MongoDB

###### Maven
```xml
<dependency>
    <groupId>com.efindi</groupId>
    <artifactId>smo-filter</artifactId>
    <version>0.0.3-RELEASE</version>
</dependency>
```

###### Gradle
```groovy
implementation 'com.efindi:smo-filter:0.0.3-RELEASE'
```

### Usage
This will throw an InvalidODataFormatException if the string input is not valid.
```java
Filter mf = new Filter("LastName eq 'Smith' and Age gt 16 and LastUpdatedDate ge datetime'2017-12-28T21:48:28Z'");
Criteria criteria = mf.getCriteria();
```


javax.validation
```java
public class RequestObject {
    @ODataFilter
    private String $filter;
}
```

### Dependencies
* org.springframework.data:spring-data-mongodb:3.4.18
* jakarta.validation:jakarta.validation-api:2.0.2
* org.apache.commons:commons-lang3:3.17.0

## Alpha
This project is still in early alpha stage. We welcome any collaborator who is interested in working for this project. 