package org.elasticsearch.externalconnector.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfiguration {
    private final EnterpriseSearchProperties properties;

    public ClientConfiguration(EnterpriseSearchProperties properties) {
        this.properties = properties;
    }

    @Bean("enterpriseSearchRestTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
            .basicAuthentication(properties.getUsername(), properties.getPassword())
            .rootUri(properties.getUrl())
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
}
