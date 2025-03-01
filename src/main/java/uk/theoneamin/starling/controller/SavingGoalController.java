package uk.theoneamin.starling.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.theoneamin.starling.enums.StarlingEndpoints;
import uk.theoneamin.starling.model.requests.savings.create.SavingGoalPayload;
import uk.theoneamin.starling.model.requests.savings.create.SavingGoalResponse;
import uk.theoneamin.starling.model.requests.savings.get.SavingsGoalResponse;
import uk.theoneamin.starling.service.SavingsGoalService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/saving-goals")
public class SavingGoalController {
    private final SavingsGoalService savingsGoalService;

    Logger log = LoggerFactory.getLogger(SavingGoalController.class);

    @GetMapping("/{accountUid}")
    public ResponseEntity<SavingsGoalResponse> getAll(@PathVariable String accountUid) {
        log.info("Endpoint: {}", StarlingEndpoints.SAVINGS_GOAL_FOR_ACCOUNT);
        return savingsGoalService.getGoals(accountUid);
    }


    @PostMapping("/{accountUid}")
    public ResponseEntity<SavingGoalResponse> create(@RequestBody SavingGoalPayload payload, @PathVariable String accountUid) {
        log.info("Endpoint: {}", StarlingEndpoints.SAVINGS_GOAL);
        return savingsGoalService.createSavingsGoal(payload, accountUid);
    }
}
