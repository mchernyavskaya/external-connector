package org.elasticsearch.externalconnector.contentsource;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.elasticsearch.externalconnector.client.EnterpriseSearchClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/source")
public class ContentSourceController {
    private final EnterpriseSearchClient client;

    public ContentSourceController(EnterpriseSearchClient client) {
        this.client = client;
    }

    @GetMapping("/{id}")
    public ContentSource getSource(@PathVariable String id) throws JsonProcessingException {
        return client.getCustomSource(id);
    }

    @PostMapping()
    public ContentSource createSource(@RequestBody ContentSource source) throws JsonProcessingException {
        return client.createSource(source);
    }
}
