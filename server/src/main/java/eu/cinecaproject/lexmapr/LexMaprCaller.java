package eu.cinecaproject.lexmapr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import eu.cinecaproject.PipelineFileUploadCaller;
import eu.cinecaproject.config.AnnotatorProperties;
import eu.cinecaproject.lexmapr.models.LexMaprResponse;
import eu.cinecaproject.model.Annotation;
import eu.cinecaproject.model.Ontology;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LexMaprCaller implements PipelineFileUploadCaller {
    private final AnnotatorProperties annotatorProperties;
    public LexMaprCaller(AnnotatorProperties annotatorProperties) {
        this.annotatorProperties = annotatorProperties;
    }

    @Override
    public LexMaprResponse call(MultipartFile file) {
        return getAnnotations(file);
    }

   public LexMaprResponse getAnnotations(MultipartFile file) {
       List<Annotation> annotations = new ArrayList<>();
       HttpResponse<String> response;
       LexMaprResponse lexMaprResponse = new LexMaprResponse();
        try {
            File ifile = new File("/tmp/f.csv");
            file.transferTo(ifile);
            Unirest.setTimeouts(0, 0);
            response = Unirest.post(annotatorProperties.getLexmaprUrl())
                    .field("inputFile", ifile)
                    .asString();
            boolean deleted = ifile.delete();
            ObjectMapper mapper = new ObjectMapper();
            lexMaprResponse = mapper.readValue(response.getBody(), LexMaprResponse.class);
        }catch (UnirestException | IOException unx){
            lexMaprResponse.setError(unx.getMessage());
        }
       return lexMaprResponse;

    }
}