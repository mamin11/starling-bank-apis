package uk.theoneamin.starling.model.requests.savings.create;

import lombok.Builder;
import lombok.Data;

/**
 * Response object for creating a saving goal that we receive from the Starling API
 */
@Data
@Builder
public class SavingGoalResponse {
    private String savingsGoalUid;
    private boolean success;
}