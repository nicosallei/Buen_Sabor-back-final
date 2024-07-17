package NetDevops.BuenSabor.service.util;


import NetDevops.BuenSabor.entities.LocalidadApiResponse;
import NetDevops.BuenSabor.entities.Municipio;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ApiClienteService {

    private final RestTemplate restTemplate;

    public ApiClienteService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Municipio> obtenerLocalidadesPorProvincia(String provinciaId) {
        String url = "https://apis.datos.gob.ar/georef/api/municipios?provincia=" + provinciaId + "&campos=id,nombre&max=100";
        ResponseEntity<LocalidadApiResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<LocalidadApiResponse>() {});
        return response.getBody().getMunicipios();
    }
}