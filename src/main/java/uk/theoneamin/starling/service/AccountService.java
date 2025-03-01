package uk.theoneamin.starling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.theoneamin.starling.config.RestTemplateConfig;
import uk.theoneamin.starling.config.RestTemplateWrapper;
import uk.theoneamin.starling.entity.AccountEntity;
import uk.theoneamin.starling.enums.StarlingEndpoints;
import uk.theoneamin.starling.exception.AccountsException;
import uk.theoneamin.starling.model.http.BaseStarlingRequest;
import uk.theoneamin.starling.model.requests.accounts.AccountResponse;
import uk.theoneamin.starling.model.requests.accounts.MainAccountsResponse;
import uk.theoneamin.starling.repository.AccountRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final RestTemplateConfig restTemplateConfig;
    private final AccountRepository accountRepository;

    public ResponseEntity<MainAccountsResponse> getAccounts() {
        return ResponseEntity.ok(MainAccountsResponse.builder()
                .accounts(accountRepository.findAll().stream().map(this::mapToResponse).toArray(AccountResponse[]::new))
                .build());
    }

    public ResponseEntity<MainAccountsResponse> refreshAccounts() {
        // get from starling
        BaseStarlingRequest<Void, MainAccountsResponse> request = BaseStarlingRequest.
                <Void, MainAccountsResponse>builder()
                .endpoint(StarlingEndpoints.ACCOUNTS)
                .headers(this.restTemplateConfig.getHeaders())
                .responseType(MainAccountsResponse.class)
                .build();

        RestTemplateWrapper<Void, MainAccountsResponse> restTemplateWrapper = RestTemplateWrapper
                .<Void, MainAccountsResponse>builder()
                .restTemplate(this.restTemplateConfig.createRestTemplate())
                .payload(request)
                .build();

        ResponseEntity<MainAccountsResponse> response = restTemplateWrapper.sendRequest();

        if (!response.getStatusCode().is2xxSuccessful()){
            throw new AccountsException("Failed to get accounts from Starling");
        }

        AccountResponse[] accountResponses = response.getBody().getAccounts();
        List<AccountEntity> accountEntities = Arrays.stream(accountResponses).map(this::mapToEntity).toList();

        // save to DB
        accountRepository.saveAll(accountEntities);

        return ResponseEntity.ok(MainAccountsResponse.builder()
                .accounts(accountResponses)
                .build());
    }

    private AccountResponse mapToResponse(AccountEntity accountEntity) {
        return AccountResponse.builder()
                .accountUid(accountEntity.getAccountUid())
                .name(accountEntity.getName())
                .accountType(accountEntity.getAccountType())
                .defaultCategory(accountEntity.getDefaultCategory())
                .currency(accountEntity.getCurrency())
                .createdAt(accountEntity.getCreatedAt())
                .build();
    }

    private AccountEntity mapToEntity(AccountResponse accountResponse) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountUid(accountResponse.getAccountUid());
        accountEntity.setName(accountResponse.getName());
        accountEntity.setAccountType(accountResponse.getAccountType());
        accountEntity.setDefaultCategory(accountResponse.getDefaultCategory());
        accountEntity.setCurrency(accountResponse.getCurrency());
        accountEntity.setCreatedAt(accountResponse.getCreatedAt());
        return accountEntity;
    }
}
