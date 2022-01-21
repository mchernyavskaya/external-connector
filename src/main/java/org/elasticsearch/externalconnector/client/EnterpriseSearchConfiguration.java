package org.elasticsearch.externalconnector.client;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "enterprise-search")
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Slf4j
public class EnterpriseSearchConfiguration {
    private String url;
    private String username;
    private String password = "";

    @PostConstruct
    public void load() {
        log.info("EnterpriseSearchConfiguration loaded with: {}", this);
    }

}
