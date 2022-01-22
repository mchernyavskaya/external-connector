package org.elasticsearch.externalconnector.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Configuration
public class ClientConfiguration {
    private final EnterpriseSearchProperties properties;

    public ClientConfiguration(EnterpriseSearchProperties properties) {
        this.properties = properties;
    }

    @Bean("enterpriseSearchRestTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        BufferingClientHttpRequestFactory bufferingFactory = new BufferingClientHttpRequestFactory(requestFactory);
        requestFactory.setOutputStreaming(false);
        return builder
            .basicAuthentication(properties.getUsername(), properties.getPassword())
            .requestFactory(() -> bufferingFactory)
            .rootUri(properties.getUrl())
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .additionalInterceptors(loggingInterceptor())
            .build();
    }

    @Bean
    public ClientHttpRequestInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }

    @Slf4j
    public static class LoggingInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(
            HttpRequest request, byte[] requestBody, ClientHttpRequestExecution ex) throws IOException {
            if (log.isDebugEnabled()) {
                log.debug("################");
                log.debug("Request body: {}", new String(requestBody, StandardCharsets.UTF_8));
            }
            ClientHttpResponse response = ex.execute(request, requestBody);
            InputStreamReader isr = new InputStreamReader(
                response.getBody(), StandardCharsets.UTF_8);
            String body = new BufferedReader(isr).lines()
                .collect(Collectors.joining("\n"));
            if (log.isDebugEnabled()) {
                log.debug("################");
                log.debug("Response body: {}", body);
            }
            return response;
        }
    }
}
