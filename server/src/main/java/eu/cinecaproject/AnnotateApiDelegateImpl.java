package eu.cinecaproject;

import eu.cinecaproject.api.AnnotateApiDelegate;
import eu.cinecaproject.model.AnnotatedText;
import eu.cinecaproject.model.Annotation;
import eu.cinecaproject.model.ModelParam;
import eu.cinecaproject.zooma.ZoomaCaller;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnotateApiDelegateImpl implements AnnotateApiDelegate {
    private final ZoomaCaller zoomaCaller;

    public AnnotateApiDelegateImpl(ZoomaCaller zoomaCaller) {
        this.zoomaCaller = zoomaCaller;
    }

    @Override
    public ResponseEntity<AnnotatedText> annotateGet(ModelParam model,
                                                     List<String> concept,
                                                     String text) {
        AnnotatedText annotatedText = new AnnotatedText().text(text)
                .annotations(new ArrayList<>());

        List<Annotation> annotations = annotatedText.getAnnotations();
//        Annotation annotation = new Annotation();
//        annotations.add(annotation);
//        annotation.setText(text);
//        annotation.setOntology(new Ontology().id("GE:0000530").label("Diabetes"));
//        annotation.setScore(new BigDecimal(80));
//        annotation.setSource("ZOOMA");

        Annotation zoomaAnnotation = zoomaCaller.call(text, concept.toString());
        annotations.add(zoomaAnnotation);

        return ResponseEntity.ok(annotatedText);
    }

}
