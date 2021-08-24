package eu.cinecaproject.zooma.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZoomaResponse {
    private String uri;
    private ZoomaAnnotatedProperty annotatedProperty;
    private ObjectNode _links;
    private List<String> semanticTags;
    private List<ObjectNode> replacedBy;
    private List<ObjectNode> replaces;
    private ObjectNode derivedFrom;
    private String confidence;
    private List<ObjectNode> annotatedBiologicalEntities;
    private ZoomaProvenance provenance;
}

@Data
class ZoomaAnnotation {
    private String uri;
    private ZoomaAnnotatedProperty annotatedProperty;
    private String _links;
    private List<String> semanticTags;
    private List<String> replacedBy;
    private List<String> replaces;
    private String derivedFrom;
    private String confidence;
    private List<String> annotatedBiologicalEntities;
    private ZoomaProvenance provenance;
}

@Data
class ZoomaAnnotatedProperty {
    private String uri;
    private String propertyType;
    private String propertyValue;
}

@Data
class ZoomaProvenance {
    private ObjectNode source;
    private String evidence;
    private String accuracy;
    private String generator;
    private String generatedDate;
    private String annotator;
    private String annotationDate;
}
