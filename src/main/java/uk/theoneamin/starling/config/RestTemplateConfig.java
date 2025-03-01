package uk.theoneamin.starling.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Configuration for RestTemplate and http headers.
 */
@Service
@Configuration
public class RestTemplateConfig {
    @Value("${bearer.token}")
    private String bearerToken;

    public HttpHeaders getHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + this.bearerToken);

        return headers;
    }

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }
}
