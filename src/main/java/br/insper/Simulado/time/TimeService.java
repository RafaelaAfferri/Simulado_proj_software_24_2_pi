package br.insper.Simulado.time;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TimeService {
    public ResponseEntity<Time> getTime(Integer indentificador) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(
                "http://campeonato:8080/time/" + indentificador,
                Time.class);

    }
}
