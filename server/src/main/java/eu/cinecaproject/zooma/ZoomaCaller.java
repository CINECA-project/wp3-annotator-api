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
import java.util.ArrayList;
import java.util.List;
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
    public List<Annotation> call(String text, String concept) {
        return getAnnotations(text, concept);
    }

    public List<Annotation> getAnnotations(String text, String concept) {
        String zoomaUrl = UriComponentsBuilder
                .fromHttpUrl(annotatorProperties.getZoomaUrl())
                .queryParam("propertyValue", text)
                .queryParam("propertyType", concept)
                .build().toString();
        ZoomaResponse[] response = restTemplate.getForObject(zoomaUrl, ZoomaResponse[].class);

        List<Annotation> annotations = new ArrayList<>(response.length);
        if (response != null && response.length > 0) {
            for (ZoomaResponse res : response) {
                String iri = res.getSemanticTags().get(0);
                String confidence = res.getConfidence();
                int score = "HIGH".equalsIgnoreCase(confidence) ? 80 : 50;
                Ontology ontology = ontologyLookup.lookup(iri).orElse(new Ontology());

                annotations.add(new Annotation().text(text)
                                                         .ontology(ontology)
                                                         .score(new BigDecimal(score))
                                                         .source("Zooma"));
            }
        }

        return annotations;
    }


//    public static void main(String[] args) {
//        ZoomaCaller zoomaCaller = new ZoomaCaller();
//        zoomaCaller.call1("mus musculus", "organism");
//    }
}
