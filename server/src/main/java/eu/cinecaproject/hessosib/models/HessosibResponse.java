package eu.cinecaproject.hessosib.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import eu.cinecaproject.model.Ontology;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HessosibResponse {
    private Ontology ontology;
    private float score;
    private String source;
    private String text;
}