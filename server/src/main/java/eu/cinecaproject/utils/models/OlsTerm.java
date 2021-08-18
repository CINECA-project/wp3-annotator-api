package eu.cinecaproject.utils.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

import java.util.List;

@Data
public class OlsTerm {
    private String iri;
    private String label;
    private List<String> description;
    private ObjectNode annotation;
    @JsonProperty("ontology_name")
    private String ontologyName;
    @JsonProperty("ontology_prefix")
    private String ontologyPrefix;
    @JsonProperty("ontology_iri")
    private String ontologyIri;
    @JsonProperty("short_form")
    private String shortForm;
    @JsonProperty("obo_id")
    private String oboId;
}
