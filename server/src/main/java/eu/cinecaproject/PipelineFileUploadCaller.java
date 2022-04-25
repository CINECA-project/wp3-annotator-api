package eu.cinecaproject;

import eu.cinecaproject.lexmapr.models.LexMaprResponse;
import eu.cinecaproject.model.Annotation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PipelineFileUploadCaller {
    LexMaprResponse call(MultipartFile file);
}