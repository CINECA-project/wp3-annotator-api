package eu.cinecaproject;


import eu.cinecaproject.api.ModelsApiDelegate;
import eu.cinecaproject.model.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelsApiDelegateImpl implements ModelsApiDelegate {

    @Override
    public ResponseEntity<List<Model>> modelsGet() {
        List<Model> modelList = new ArrayList<>();
//        modelList.add(new Model().modelName("ZOOMA")
//                .description("EBI Zooma service for annotating free text")
//                .concepts(List.of(Model.ConceptsEnum.DISEASE)));
//        modelList.add(new Model().modelName("HESSO")
//                .description("HESSO SIB pipeline")
//                .concepts(List.of(Model.ConceptsEnum.DISEASE, Model.ConceptsEnum.DRUG)));
//        modelList.add(new Model().modelName("LEXMAPR")
//                .description("LexMapr")
//                .concepts(List.of(Model.ConceptsEnum.DISEASE, Model.ConceptsEnum.DRUG)));
//        modelList.add(new Model().modelName("SORTA")
//                .description("SORTA")
//                .concepts(List.of(Model.ConceptsEnum.DISEASE, Model.ConceptsEnum.DRUG)));
        return ResponseEntity.ok(modelList);
    }

}
