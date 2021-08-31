package eu.cinecaproject;

import eu.cinecaproject.model.Annotation;

import java.util.List;

public interface PipelineCaller {
    List<Annotation> call(String text, String concept);
}
