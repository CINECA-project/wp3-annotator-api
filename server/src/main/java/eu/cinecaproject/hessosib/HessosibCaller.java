package eu.cinecaproject.hessosib;

import eu.cinecaproject.PipelineCaller;
import eu.cinecaproject.config.AnnotatorProperties;
import eu.cinecaproject.hessosib.models.HessosibResponse;
import eu.cinecaproject.model.Annotation;
import eu.cinecaproject.model.Ontology;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class HessosibCaller implements PipelineCaller {
    private final AnnotatorProperties annotatorProperties;
    private final RestTemplate restTemplate;

    public HessosibCaller(AnnotatorProperties annotatorProperties) {
        this.annotatorProperties = annotatorProperties;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Annotation call(String text, String concept) {
        return getAnnotations(text).orElse(new Annotation());
    }

    public Optional<Annotation> getAnnotations(String text) {
        String hessosibUrl = UriComponentsBuilder
                .fromHttpUrl(annotatorProperties.getHessosibUrl())
                .queryParam("text", text)
                .build().toString();
        RequestEntity<Void> request = RequestEntity.get(hessosibUrl)
                                                   .accept(MediaType.APPLICATION_JSON).build();
        Map<String, HessosibResponse> response =
                restTemplate.exchange(request, new ParameterizedTypeReference<Map<String, HessosibResponse>>() {
                }).getBody();

        Optional<Annotation> annotation;
        if (response != null && response.containsKey("0")) {
            float score = response.get("0").getScore();
            Ontology ontology = response.get("0").getOntology();

            annotation = Optional.of(new Annotation().text(text)
                                                     .ontology(ontology).score(new BigDecimal(score))
                                                     .source("HES-SO/SIB"));
        } else {
            annotation = Optional.empty();
        }

        return annotation;
    }
}