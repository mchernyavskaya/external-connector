package org.elasticsearch.externalconnector.client;

import lombok.SneakyThrows;
import org.elasticsearch.externalconnector.model.ContentSource;
import org.elasticsearch.externalconnector.util.ObjectMappers;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

@Service
public class EnterpriseSearchClient {
    private final EnterpriseSearchConfiguration configuration;
    private final WebClient client;

    public EnterpriseSearchClient(EnterpriseSearchConfiguration configuration) {
        this.configuration = configuration;
        this.client = WebClient.builder()
            .baseUrl(configuration.getUrl())
            .filter(ExchangeFilterFunctions.basicAuthentication(
                configuration.getUsername(),
                configuration.getPassword()))
            .build();
    }

    public Mono<ContentSource> createSource(ContentSource source) {
        try {
            @SuppressWarnings("BlockingMethodInNonBlockingContext")
            String body = ObjectMappers.SNAKE_CASE.writeValueAsString(source);
            return client.post()
                .uri("/sources")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .toEntity(String.class)
                .onErrorMap(new Function<Throwable, Throwable>() {
                    @Override
                    public Throwable apply(Throwable throwable) {
                        return new EnterpriseSearchClientException("Error creating a source", throwable);
                    }
                })
                .publish(new Function<Mono<ResponseEntity<String>>, Mono<? extends ContentSource>>() {
                    @Override
                    public Mono<ContentSource> apply(Mono<ResponseEntity<String>> responseMono) {
                        return responseMono.map(new Function<ResponseEntity<String>, ContentSource>() {
                            @SneakyThrows
                            @Override
                            public ContentSource apply(ResponseEntity<String> stringEntity) {
                                return ObjectMappers.SNAKE_CASE.readValue(stringEntity.getBody(), ContentSource.class);
                            }
                        });
                    }
                });
        } catch (IOException e) {
            throw new EnterpriseSearchClientException("Error creating a source: " + source, e);
        }
    }

    public ContentSource getCustomSource(String id) {
        try {
            ResponseEntity<String> response = client.post()
                .uri("/sources/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class)
                .block();
            ResponseEntity<String> responseEntity = Objects.requireNonNull(response);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return ObjectMappers.SNAKE_CASE.readValue(response.getBody(), ContentSource.class);
            }
            throw new EnterpriseSearchClientException(
                "Error getting a source by ID: " + id,
                ObjectMappers.DEFAULT.readValue(response.getBody(), ErrorResponse.class)
            );
        } catch (IOException e) {
            throw new EnterpriseSearchClientException("Error getting a source by ID: " + id, e);
        }
    }
}
