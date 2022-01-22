package org.elasticsearch.externalconnector.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

public class ObjectMappers {
    public static ObjectMapper DEFAULT = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);

    public static ObjectMapper SNAKE_CASE = new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
}
