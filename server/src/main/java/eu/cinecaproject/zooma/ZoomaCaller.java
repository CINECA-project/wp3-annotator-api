package eu.cinecaproject.zooma;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.cinecaproject.PipelineCaller;
import eu.cinecaproject.config.AnnotatorProperties;
import eu.cinecaproject.model.Annotation;
import eu.cinecaproject.model.Ontology;
import eu.cinecaproject.utils.OntologyLookup;
import eu.cinecaproject.zooma.models.ZoomaResponse;
import org.glassfish.jersey.uri.UriComponent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ZoomaCaller implements PipelineCaller {
    private final String url;
    private final RestTemplate restTemplate;
    private final OntologyLookup ontologyLookup;
    private final AnnotatorProperties annotatorProperties;

    public ZoomaCaller(OntologyLookup ontologyLookup, AnnotatorProperties annotatorProperties) {
        this.url = "https://www.ebi.ac.uk/spot/zooma/v2/api/services/annotate";
        this.restTemplate = new RestTemplate();
        this.ontologyLookup = ontologyLookup;
        this.annotatorProperties = annotatorProperties;
    }

    @Override
    public Annotation call(String text, String concept) {
        return getAnnotations(text, concept).orElse(new Annotation());//todo
    }


    public Annotation call1(String text, String concept) {
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


    public Optional<Annotation> getAnnotations(String text, String concept) {
        String zoomaUrl = UriComponentsBuilder
                .fromHttpUrl(annotatorProperties.getZoomaUrl())
                .queryParam("propertyValue", text)
                .queryParam("propertyType", concept)
                .build().toString();
        ZoomaResponse[] response = restTemplate.getForObject(zoomaUrl, ZoomaResponse[].class);

        Optional<Annotation> annotation;
        if (response != null && response.length > 0) {
            String iri = response[0].getSemanticTags().get(0);
            String confidence = response[0].getConfidence();
            int score = confidence.equalsIgnoreCase("HIGH") ? 80 : 50;
            Ontology ontology = ontologyLookup.lookup(iri).orElse(new Ontology());//todo

            annotation = Optional.of(new Annotation().text(text)
                    .ontology(ontology).score(new BigDecimal(score)).source("Zooma"));
        } else {
            annotation = Optional.empty();
        }

        return annotation;
    }


//    public static void main(String[] args) {
//        ZoomaCaller zoomaCaller = new ZoomaCaller();
//        zoomaCaller.call1("mus musculus", "organism");
//    }
}
