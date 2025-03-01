package uk.theoneamin.starling.model.requests.accounts;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MainAccountsResponse {
    private AccountResponse[] accounts;
}