package eu.cinecaproject.zooma;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cinecaproject.PipelineCaller;
import eu.cinecaproject.model.Annotation;
import eu.cinecaproject.model.Ontology;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class ZoomaCaller implements PipelineCaller {
    private final String url;
    private final RestTemplate restTemplate;

    public ZoomaCaller() {
        this.url = "https://www.ebi.ac.uk/spot/zooma/v2/api/services/annotate";
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Annotation call(String text, String concept) {
        ResponseEntity<String> response = restTemplate.getForEntity(url + "?propertyValue=" + text + "&propertyType=" + concept, String.class);
        System.out.println(response.toString());

        String iri = "error";
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            if (root.size() > 0) {
                JsonNode first = root.get(0);
                if (first.has("semanticTags") && first.get("semanticTags").size() > 0) {
                    iri = first.get("semanticTags").get(0).asText();
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new Annotation().text(text)
                .ontology(new Ontology().id(iri).label("placeholder"))
                .score(new BigDecimal(81))
                .source("ZOOMA");
    }

    public static void main(String[] args) {
        ZoomaCaller zoomaCaller = new ZoomaCaller();
        zoomaCaller.call("mus musculus", "organism");
    }
}
