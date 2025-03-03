package uk.theoneamin.starling.model.requests.savings.get;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SavingsGoalResponse {
    private SavingsGoal[] savingsGoalList;
}