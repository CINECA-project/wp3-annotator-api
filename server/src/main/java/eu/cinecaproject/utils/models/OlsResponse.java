package eu.cinecaproject.utils.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OlsResponse {
    @JsonProperty("_embedded")
    private OlsEmbeddedResponse embedded;
    @JsonProperty("_links")
    private ObjectNode links;
    private Page page;
}

