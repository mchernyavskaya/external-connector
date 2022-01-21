package org.elasticsearch.externalconnector.client;

import lombok.Value;

import java.util.List;

@Value
public class PagedResponse<T> {
    Meta meta;
    List<T> results;

    @Value
    public static class Meta {
        @Value
        public static class Page {
            int current;
            int totalPages;
            int totalResults;
            int size;
        }
    }
}
