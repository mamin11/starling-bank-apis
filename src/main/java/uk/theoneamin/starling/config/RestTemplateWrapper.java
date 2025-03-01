package uk.theoneamin.starling.config;

import lombok.Builder;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.theoneamin.starling.model.http.BaseStarlingRequest;

@Builder
public class RestTemplateWrapper<T, R> {
    private RestTemplate  restTemplate;
    private BaseStarlingRequest<T, R> payload;

    private final Logger log = org.slf4j.LoggerFactory.getLogger(RestTemplateWrapper.class);

    public void sendVoidRequest() {
        log.info("Sending request: {}", this.payload);

        if (this.payload.getParams() == null) {
            this.restTemplate.exchange(
                    this.payload.getEndpoint().getUrl(),
                    this.payload.getEndpoint().getMethod(),
                    this.payload.httpPayload(), Void.class);
            return;
        }

        this.restTemplate.exchange(
                this.payload.getEndpoint().getUrl(),
                this.payload.getEndpoint().getMethod(),
                this.payload.httpPayload(),
                Void.class,
                this.payload.getParams());
    }

    public ResponseEntity<R> sendRequest() {
        log.info("Sending request: {}", this.payload);

        if (this.payload.getParams() == null) {
            return this.restTemplate.exchange(
                    this.payload.getEndpoint().getUrl(),
                    this.payload.getEndpoint().getMethod(),
                    this.payload.httpPayload(),
                    this.payload.getResponseType());
        }

        return this.restTemplate.exchange(
                this.payload.getEndpoint().getUrl(),
                this.payload.getEndpoint().getMethod(),
                this.payload.httpPayload(),
                this.payload.getResponseType(),
                this.payload.getParams());
    }
}
