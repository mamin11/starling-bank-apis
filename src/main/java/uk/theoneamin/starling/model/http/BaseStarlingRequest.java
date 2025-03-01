package uk.theoneamin.starling.model.http;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import uk.theoneamin.starling.enums.StarlingEndpoints;

import java.util.Arrays;

/**
 * Base request object for Starling API that contains headers, payload, response type, endpoint and any other params
 * @param <P> payload
 * @param <R> response
 */
@Data
@Builder
public class BaseStarlingRequest<P, R> {
    private HttpHeaders headers;
    private P payload;
    private Class<R> responseType;
    private StarlingEndpoints endpoint;
    private Object[] params;

    public HttpEntity<P> httpPayload() {
        if (this.payload == null) {
            return new HttpEntity<>(this.headers);
        }

        return new HttpEntity<>(this.payload, this.headers);
    }

    // override toString method to exclude headers when printing the request to logs
    @Override
    public String toString() {
        return "BaseStarlingRequest{" +
                "payload=" + payload +
                ", responseType=" + responseType +
                ", endpoint=" + endpoint +
                ", params=" + Arrays.toString(params) +
                '}';
    }

}
