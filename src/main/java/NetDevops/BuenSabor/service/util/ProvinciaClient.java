package NetDevops.BuenSabor.service.util;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ProvinciaClient {
    private final WebClient webClient;

    public ProvinciaClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://apis.datos.gob.ar").build();
    }

    public String fetchProvincias() {
        return this.webClient.get().uri("/georef/api/provincias?campos=id,nombre")
                .retrieve().bodyToMono(String.class).block();
    }
}