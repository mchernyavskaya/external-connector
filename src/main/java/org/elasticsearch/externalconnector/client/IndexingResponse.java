package org.elasticsearch.externalconnector.client;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class IndexingResponse {
    List<Result> results;

    public List<Result> errors() {
        return results == null ? Collections.emptyList() : results.stream().filter(Result::hasError).toList();
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class Result {
        String id;
        List<String> errors;

        public boolean hasError() {
            return !CollectionUtils.isEmpty(errors);
        }
    }
}
