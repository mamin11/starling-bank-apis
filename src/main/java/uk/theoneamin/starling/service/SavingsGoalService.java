package uk.theoneamin.starling.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.theoneamin.starling.config.RestTemplateConfig;
import uk.theoneamin.starling.config.RestTemplateWrapper;
import uk.theoneamin.starling.model.http.BaseStarlingRequest;
import uk.theoneamin.starling.enums.StarlingEndpoints;
import uk.theoneamin.starling.model.requests.savings.create.SavingGoalPayload;
import uk.theoneamin.starling.model.requests.savings.create.SavingGoalResponse;
import uk.theoneamin.starling.model.requests.savings.get.SavingsGoalResponse;

@Service
@RequiredArgsConstructor
public class SavingsGoalService {
    private final RestTemplateConfig restTemplateConfig;

    public ResponseEntity<SavingsGoalResponse> getGoals(String accountUid) {
        BaseStarlingRequest<Void, SavingsGoalResponse> request = BaseStarlingRequest.
                <Void, SavingsGoalResponse>builder()
                .endpoint(StarlingEndpoints.SAVINGS_GOAL_FOR_ACCOUNT)
                .headers(this.restTemplateConfig.getHeaders())
                .params(new Object[]{accountUid})
                .responseType(SavingsGoalResponse.class)
                .build();

        RestTemplateWrapper<Void, SavingsGoalResponse> restTemplateWrapper = RestTemplateWrapper
                .<Void, SavingsGoalResponse>builder()
                .restTemplate(this.restTemplateConfig.createRestTemplate())
                .payload(request)
                .build();

        return restTemplateWrapper.sendRequest();
    }

    public ResponseEntity<SavingGoalResponse> createSavingsGoal(SavingGoalPayload savingGoalPayload, String accountUid) {
        BaseStarlingRequest<SavingGoalPayload, SavingGoalResponse> request = BaseStarlingRequest
                .<SavingGoalPayload, SavingGoalResponse>builder()
                .endpoint(StarlingEndpoints.SAVINGS_GOAL)
                .headers(this.restTemplateConfig.getHeaders())
                .payload(savingGoalPayload)
                .params(new Object[]{accountUid})
                .responseType(SavingGoalResponse.class)
                .build();

        RestTemplateWrapper<SavingGoalPayload, SavingGoalResponse> restTemplateWrapper = RestTemplateWrapper
                .<SavingGoalPayload, SavingGoalResponse>builder()
                .restTemplate(this.restTemplateConfig.createRestTemplate())
                .payload(request)
                .build();

        return restTemplateWrapper.sendRequest();
    }

}

