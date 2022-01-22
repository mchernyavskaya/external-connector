package org.elasticsearch.externalconnector.contentsource;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Document {
    String url;
    String title;
    String body;
    String id;
    String contentSourceId;
    Date lastUpdated;
}
