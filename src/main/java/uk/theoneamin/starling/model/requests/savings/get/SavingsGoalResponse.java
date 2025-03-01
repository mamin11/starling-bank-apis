package uk.theoneamin.starling.model.requests.savings.get;

import lombok.Builder;
import lombok.Data;
import uk.theoneamin.starling.enums.SavingGoalStatus;
import uk.theoneamin.starling.model.requests.CurrencyAmount;

@Data
@Builder
public class SavingsGoalResponse {
    private SavingsGoal[] savingsGoalList;
}

@Data
@Builder
class SavingsGoal {
    private String savingsGoalUid;
    private String name;
    private CurrencyAmount target;
    private CurrencyAmount totalSaved;
    private int savedPercentage;
    private SavingGoalStatus state;
}