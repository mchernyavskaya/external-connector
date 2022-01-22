package org.elasticsearch.externalconnector.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.elasticsearch.externalconnector.contentsource.ContentSource;
import org.elasticsearch.externalconnector.contentsource.Document;
import org.elasticsearch.externalconnector.util.ObjectMappers;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class EnterpriseSearchClient {
    private final RestTemplate restTemplate;

    public EnterpriseSearchClient(@Qualifier("enterpriseSearchRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ContentSource createSource(ContentSource source) {
        try {
            String body = ObjectMappers.SNAKE_CASE.writeValueAsString(source);
            ResponseEntity<String> response = restTemplate.postForEntity("/sources", body, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return ObjectMappers.SNAKE_CASE.readValue(response.getBody(), ContentSource.class);
            } else {
                throw new EnterpriseSearchClientException(
                    "Error creating a source: " + source,
                    ObjectMappers.SNAKE_CASE.readValue(response.getBody(), ErrorResponse.class)
                );
            }
        } catch (JsonProcessingException e) {
            throw new EnterpriseSearchClientException(
                "Error creating custom source: " + source, e
            );
        }
    }

    public ContentSource getCustomSource(String id) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(
                "/sources/" + id,
                String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return ObjectMappers.SNAKE_CASE.readValue(response.getBody(), ContentSource.class);
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            } else {
                throw new EnterpriseSearchClientException(
                    "Error getting a source: " + id,
                    ObjectMappers.SNAKE_CASE.readValue(response.getBody(), ErrorResponse.class)
                );
            }
        } catch (JsonProcessingException e) {
            throw new EnterpriseSearchClientException(
                "Error getting custom source: " + id, e
            );
        }
    }

    public IndexingResponse index(String sourceId, Document document) {
        return indexBatch(sourceId, Collections.singletonList(document));
    }

    public IndexingResponse indexBatch(String sourceId, List<Document> documents) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                "/sources/" + sourceId + "/documents/bulk_create",
                ObjectMappers.SNAKE_CASE.writeValueAsString(documents),
                String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return ObjectMappers.SNAKE_CASE.readValue(response.getBody(), IndexingResponse.class);
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            } else {
                throw new EnterpriseSearchClientException(
                    "Error indexing documents: " + documents,
                    ObjectMappers.SNAKE_CASE.readValue(response.getBody(), ErrorResponse.class)
                );
            }
        } catch (JsonProcessingException e) {
            throw new EnterpriseSearchClientException(
                "Error indexing documents: " + documents, e
            );
        }
    }
}
