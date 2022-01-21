package org.elasticsearch.externalconnector.controller;

import org.elasticsearch.externalconnector.client.EnterpriseSearchClient;
import org.elasticsearch.externalconnector.model.ContentSource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/source")
public class ContentSourceController {
    private final EnterpriseSearchClient client;

    public ContentSourceController(EnterpriseSearchClient client) {
        this.client = client;
    }

    @GetMapping("/{id}")
    public ContentSource getSource(@PathVariable String id) {
        return client.getCustomSource(id);
    }

    @PostMapping()
    public ContentSource createSource(@RequestBody ContentSource source) {
        return client.createSource(source);
    }
}
