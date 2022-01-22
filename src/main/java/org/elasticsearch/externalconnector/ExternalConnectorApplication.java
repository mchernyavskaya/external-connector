package org.elasticsearch.externalconnector;

import org.elasticsearch.externalconnector.client.EnterpriseSearchProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(EnterpriseSearchProperties.class)
public class ExternalConnectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalConnectorApplication.class, args);
    }
}
