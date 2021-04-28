package eu.cinecaproject;


import eu.cinecaproject.api.ModelsApiDelegate;
import eu.cinecaproject.model.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelsApiDelegateImpl implements ModelsApiDelegate {

    @Override
    public ResponseEntity<List<Model>> modelsGet() {
        return ResponseEntity.ok(null);
    }

}
