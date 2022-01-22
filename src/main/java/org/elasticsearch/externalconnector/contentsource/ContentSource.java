package org.elasticsearch.externalconnector.contentsource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentSource {
    String id;
    String name;

    public ContentSource(String name) {
        this.name = name;
    }
}
