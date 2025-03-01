package uk.theoneamin.starling.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.theoneamin.starling.model.requests.accounts.MainAccountsResponse;
import uk.theoneamin.starling.service.AccountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    @GetMapping
    public ResponseEntity<MainAccountsResponse> getAccounts() {
        log.info("Getting accounts from DB");
        return accountService.getAccounts();
    }

    @PostMapping("/refresh")
    public ResponseEntity<MainAccountsResponse> refreshAccounts() {
        log.info("Refreshing accounts from Starling");
        return accountService.refreshAccounts();
    }

}
