package eu.cinecaproject.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AnnotatorProperties {

    @Value("${ebi.zooma.url:https://www.ebi.ac.uk/spot/zooma/v2/api/services/annotate}")
    private String zoomaUrl;

    @Value("${ebi.ols.url:https://www.ebi.ac.uk/ols/api/terms}")
    private String olsUrl;
}
