package eu.cinecaproject;

import eu.cinecaproject.api.AnnotateApiDelegate;
import eu.cinecaproject.hessosib.HessosibCaller;
import eu.cinecaproject.model.AnnotatedText;
import eu.cinecaproject.model.Annotation;
import eu.cinecaproject.model.FileUploadResponse;
import eu.cinecaproject.model.ModelParam;
import eu.cinecaproject.zooma.ZoomaCaller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AnnotateApiDelegateImpl implements AnnotateApiDelegate {
    private final ZoomaCaller zoomaCaller;
    private final HessosibCaller hessosibCaller;

    public AnnotateApiDelegateImpl(ZoomaCaller zoomaCaller, HessosibCaller hessosibCaller) {
        this.zoomaCaller = zoomaCaller;
        this.hessosibCaller = hessosibCaller;
    }

    @Override
    public ResponseEntity<AnnotatedText> annotateGet(ModelParam model,
                                                     List<String> concept,
                                                     String text) {
        AnnotatedText annotatedText = new AnnotatedText().text(text).annotations(new ArrayList<>());
        List<Annotation> annotations = annotatedText.getAnnotations();
        List<Annotation> annotation;
        switch (model) {
        case HESSO_SIB:
            annotation = hessosibCaller.call(text, concept.toString());
            break;
        case LEXMAPR:
        case SORTA:
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        case ZOOMA:
        default:
            annotation = zoomaCaller.call(text, concept.toString());
            break;
        }
        annotations.addAll(annotation);

        return ResponseEntity.ok(annotatedText);
    }

    @Override
    public ResponseEntity<FileUploadResponse> annotatePost(MultipartFile file,
                                                           String model,
                                                           String concept) {

        String baseUrl = "www.ebi.ac.uk/biosamples/"; //todo have a correct URL
        UUID uuid = UUID.randomUUID(); //todo store it somewhere for later use

        String url = baseUrl + uuid;
        FileUploadResponse response = new FileUploadResponse();
        response.setMessage("Your results will be available to download shortly. Please checkback later with the below link.");
        response.setLink(url);
        return ResponseEntity.ok(response);

    }

}
