package eu.cinecaproject.lexmapr.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cinecaproject.model.Ontology;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LexMaprResponse {
    @JsonProperty("status")
    private int status;
    @JsonProperty("id")
    private String id;
    @JsonProperty("output_url")
    private String outputUrl;
    @JsonProperty("msg")
    private String message = "";
    @JsonProperty("complete")
    private Boolean complete;
    @JsonProperty("error")
    private String error;
}
