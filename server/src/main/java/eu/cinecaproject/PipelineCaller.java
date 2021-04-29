package eu.cinecaproject;

import eu.cinecaproject.model.Annotation;

public interface PipelineCaller {
    Annotation call(String text, String concept);
}
