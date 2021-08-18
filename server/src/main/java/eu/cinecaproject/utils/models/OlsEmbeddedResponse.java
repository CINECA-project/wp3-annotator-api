package eu.cinecaproject.utils.models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

import java.util.List;

@Data
public class OlsEmbeddedResponse {
    private List<OlsTerm> terms;
}
