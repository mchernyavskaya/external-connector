package org.elasticsearch.externalconnector.client;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    List<String> errors;
}
