package org.elasticsearch.externalconnector.contentsource;

import lombok.Data;

@Data
public class Document {
    String url;
    String title;
    String body;
    String docId;
}
