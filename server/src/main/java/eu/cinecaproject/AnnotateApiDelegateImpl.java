package eu.cinecaproject;


import eu.cinecaproject.api.AnnotateApiDelegate;
import eu.cinecaproject.model.AnnotatedText;
import eu.cinecaproject.model.ModelParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnotateApiDelegateImpl implements AnnotateApiDelegate {

    @Override
    public ResponseEntity<AnnotatedText> annotateGet(ModelParam model,
                                                     List<String> concept,
                                                     String text) {
        return ResponseEntity.ok(null);
    }

}
