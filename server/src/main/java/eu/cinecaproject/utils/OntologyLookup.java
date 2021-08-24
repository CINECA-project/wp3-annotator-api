package eu.cinecaproject.utils;

import eu.cinecaproject.config.AnnotatorProperties;
import eu.cinecaproject.model.Ontology;
import eu.cinecaproject.utils.models.OlsResponse;
import eu.cinecaproject.utils.models.OlsTerm;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class OntologyLookup {
    private final RestTemplate restTemplate;
    private final AnnotatorProperties annotatorProperties;

    public OntologyLookup(AnnotatorProperties annotatorProperties) {
        this.restTemplate = new RestTemplate();
        this.annotatorProperties = annotatorProperties;
        String hello = "{\n" +
                "  \"type\": \"service_account\",\n" +
                "  \"project_id\": \"ihcc-cohorts\",\n" +
                "  \"private_key_id\": \"bd1794f27c1df2770319c8729c836a4ef348a85f\",\n" +
                "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC7fsNrBbhetEB7\\nPidUKgWSIPPl7EdNo3sg/G/B/zf0hX8zNYMZ7lNhqid1MfUcu1Iknk8qZMEEOPYu\\nT20sNig2UAf/wKGUhD9VX6Auq0uBGtmGbf2yzq01M26cXvDL8/7WAq90iRtNGOH2\\nXZUlE211w++bF0KL/bTNtZ3pTYfU/pQTOlz0o2EpnNxz9yqBedeZkDbpBg7e/nEv\\nJmtE3G/MF6XkScn8VfmXI86/PzHwNBsUFH0rKlt+R28aYWM96Z27xw899Mk8vNvx\\naWp6sydFEQACgxc+LE9YVU0YthJ9OW2ZIzwlLCb6idrPAEzRmXjC2EM546Cf5Mqa\\nZkBYDwYTAgMBAAECggEARWKy9N8oDLwOBM3KhiYK67oQKG1ZMc0nGtzwis1lKrNg\\nkFXkT2tYJhNvCLrVJ63Wx0BisaCZoXNm/1pbX3onRXo2i5oA8dJDEdgSklLH3V5P\\nU6VOtqYviTmgXHPyDrgb/Zz+y1SV6uEjdiXJtO/MmUFKYY2pDd900o+8gYM0mkh9\\n3/f5scS/Gj4C/25GfOX49ilwRZM5WEdVdxRWfrXYFEdnhR2LtiManZ67UGjNckEy\\nXdLHeaWouVw8bSTOpVBQHWhN5fHXlQUPDWptTMeQLVMy4gOe4voi+YTIGaQUqG5N\\nDdIfhS2F1hDa3oGuuxPvqTkzJKPPQUIP4E9MGK+LwQKBgQDdXTk39sIXJ8bXpdSL\\nvoO8UXhhmEeDvNwTCIgCLKI9tJ/NzWHTMhEcc5cLIRIpMsETCnQrQRXvKZteMGeZ\\nrX5GwZdrT+p8PPADyKGdRGgXD/qQ+73ifuyGvbhufsnCIyWMcwN27wXjjgRFORxA\\nDamI2iHl0Hq0UigI9hCOCjFA8wKBgQDY1OmJH3ZwLPQexwHo7ARY5xBNfQpCGGJr\\nrbLBWQisBruUh4rC/4Xd1qO7vfewTmvAjmapy0nMQgrS+IzeU0hrAPq2XhP0EkwJ\\nP1fFB4t5CMBB24/RYgCm/Rjg2wxIc3a8d7cm+PAy0GZ1B2Mc+yKK3wC7/WLtv8Dc\\nbXdKP89uYQKBgQCfcpTScNVhJG+fyX0No470SKKzeaKZi6Hhu9Vr64Z5d+9bqEJ8\\ng6W7nq7/fzS7pClac781oM4R1SBqeedF1KgOg0d0Lr4x+xrPPubahvmLEyFi6VHf\\nIZlxfJqqTH07b2GOhxHJy2WsgREmY3qr0Kt7PdQI22BnHn5j5goKpeDQbQKBgCrl\\nK/IhlEeqzPyNuBYBIvcHdxUtSGs88bOFoFhZqbqGdnNywQPEkxII1Uld0nNBgFWO\\nlka+3bGX2xZTb2LILQpmHjMXt3DQcdRegJmM3Y2rKoWczv3IGoLujQ5mpy5qETJF\\nqKkAFFypF6vNM1FXaUT5yuZlwT1xctDoovHAX+PhAoGBAJKDZSok8UPhc0qvtN6u\\nV3AgdkTBj8WOvF3Y9Oqv96RmjBOtEzm/ettp1uldUHXFl9VK9ReXjSDy6lUECLuO\\nZAYn9xNIJ7avRMmPOWkQTDrPSacENRVGIR4UaGUgzXQcUlN6Xg0tG8ZeDa+zkq5Z\\nIk1Plb+9YTwELojrzwYoHmNO\\n-----END PRIVATE KEY-----\\n\",\n" +
                "  \"client_email\": \"data-harmonization@ihcc-cohorts.iam.gserviceaccount.com\",\n" +
                "  \"client_id\": \"106256989414981390880\",\n" +
                "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/data-harmonization%40ihcc-cohorts.iam.gserviceaccount.com\"\n" +
                "}";
    }

    public Optional<Ontology> lookup(String iri) {
        String olsUrl = UriComponentsBuilder
                .fromHttpUrl(annotatorProperties.getOlsUrl())
                .queryParam("iri", iri)
                .queryParam("size", 1)
                .build().toString();
        OlsResponse response = restTemplate.getForObject(olsUrl, OlsResponse.class);

        Optional<Ontology> ontology;
        if (response != null && response.getEmbedded() != null) {
            OlsTerm term = response.getEmbedded().getTerms().get(0);
            String ontologyId = term.getOboId();
            String ontologyLabel = term.getLabel();
            ontology = Optional.of(new Ontology().id(ontologyId).label(ontologyLabel));
        } else {
            ontology = Optional.empty();
        }

        return ontology;
    }

//    public Ontology lookup(String iri) {
//        return new Ontology().id("id").label("label");
//    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String olsUrl = UriComponentsBuilder
                .fromHttpUrl("https://www.ebi.ac.uk/ols/api/terms")
                .queryParam("iri", "http://purl.obolibrary.org/obo/NCBITaxon_10090&size=1")
                .queryParam("size", 1)
                .build().toString();
        OlsResponse response = restTemplate.getForObject(olsUrl, OlsResponse.class);

        Optional<Ontology> ontology;
        if (response != null && response.getEmbedded() != null) {
            OlsTerm term = response.getEmbedded().getTerms().get(0);
            String ontologyId = term.getOboId();
            String ontologyLabel = term.getLabel();
            ontology = Optional.of(new Ontology().id(ontologyId).label(ontologyLabel));
        } else {
            ontology = Optional.empty();
        }

        System.out.println(ontology.get());
    }
}
