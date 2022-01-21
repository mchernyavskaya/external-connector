package org.elasticsearch.externalconnector.model;

import lombok.Data;

@Data
public class Document {
    String url;
    String title;
    String body;
    String docId;
}
