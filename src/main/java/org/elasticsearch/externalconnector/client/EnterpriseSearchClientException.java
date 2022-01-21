package org.elasticsearch.externalconnector.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

@ToString
@Getter
@Setter
public class EnterpriseSearchClientException extends RuntimeException {
    private ErrorResponse errorResponse;

    public EnterpriseSearchClientException() {
        super();
    }

    public EnterpriseSearchClientException(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public EnterpriseSearchClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnterpriseSearchClientException(String message, Throwable cause, ErrorResponse errorResponse) {
        super(message, cause);
        this.errorResponse = errorResponse;
    }

    @Override
    public String getMessage() {
        String parentMessage = super.getMessage();
        if (errorResponse == null) {
            return parentMessage;
        }
        return parentMessage +
            "\nClient Errors:\n" +
            StringUtils.collectionToDelimitedString(errorResponse.errors, "\n");
    }
}
