package uk.theoneamin.starling.model.requests;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CurrencyAmount implements Serializable {
    private String currency;
    private int minorUnits;
}
