package org.elasticsearch.externalconnector.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentSource {
    String id;
    String name;

    public ContentSource(String name) {
        this.name = name;
    }
}
