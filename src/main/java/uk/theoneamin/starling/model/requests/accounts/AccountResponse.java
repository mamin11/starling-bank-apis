package uk.theoneamin.starling.model.requests.accounts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {
    private String accountUid;
    private String name;
    private String accountType;
    private String defaultCategory;
    private String currency;
    private String createdAt;
}
