package uk.theoneamin.starling.model.requests.savings.create;

import lombok.Builder;
import lombok.Data;
import uk.theoneamin.starling.model.requests.CurrencyAmount;

import java.io.Serializable;

@Data
@Builder
public class SavingGoalPayload implements Serializable {
    private String name;
    private String currency;
    private CurrencyAmount target;
}