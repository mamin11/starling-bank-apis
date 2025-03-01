package uk.theoneamin.starling.enums;

import org.springframework.http.HttpMethod;

/**
 * Enum to hold all the endpoints for the Starling API. This is used to build the URL and the HTTP method for the given request
 * @see uk.theoneamin.starling.config.RestTemplateWrapper
 * @see uk.theoneamin.starling.model.http.BaseStarlingRequest
 */
public enum StarlingEndpoints {
    ACCOUNTS("https://api-sandbox.starlingbank.com/api/v2/accounts", HttpMethod.GET),
    SAVINGS_GOAL("https://api-sandbox.starlingbank.com/api/v2/account/{accountUid}/savings-goals", HttpMethod.PUT),
    SAVINGS_GOAL_FOR_ACCOUNT("https://api-sandbox.starlingbank.com/api/v2/account/{accountUid}/savings-goals", HttpMethod.GET),
    SAVINGS_GOAL_BY_ID("https://api-sandbox.starlingbank.com/api/v2/account/{accountUid}/savings-goals/{savingGoalUid", HttpMethod.GET),
    SAVINGS_GOAL_TRANSFER("https://api-sandbox.starlingbank.com/api/v2/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid}", HttpMethod.POST);

    private final String url;
    private final HttpMethod method;

    StarlingEndpoints(String url, HttpMethod method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public HttpMethod getMethod() {
        return method;
    }
}
