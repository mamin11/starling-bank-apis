package uk.theoneamin.starling.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.theoneamin.starling.enums.SavingGoalStatus;
import uk.theoneamin.starling.exception.GlobalExceptionHandler;
import uk.theoneamin.starling.model.requests.CurrencyAmount;
import uk.theoneamin.starling.model.requests.savings.create.SavingGoalPayload;
import uk.theoneamin.starling.model.requests.savings.create.SavingGoalResponse;
import uk.theoneamin.starling.model.requests.savings.get.SavingsGoal;
import uk.theoneamin.starling.model.requests.savings.get.SavingsGoalResponse;
import uk.theoneamin.starling.service.SavingsGoalService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SavingGoalControllerTest {
    @InjectMocks
    private SavingGoalController savingGoalController;

    @Mock
    private SavingsGoalService savingsGoalService;

    private MockMvc mvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(savingGoalController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetAllSavings() throws Exception {
        // given
        SavingsGoalResponse savingsGoalResponse = SavingsGoalResponse
                .builder()
                .savingsGoalList(new SavingsGoal[]{
                        SavingsGoal
                                .builder()
                                .name("name")
                                .savingsGoalUid("savingsGoalUid")
                                .savedPercentage(0)
                                .state(SavingGoalStatus.ACTIVE)
                                .totalSaved(CurrencyAmount.builder().currency("GBP").minorUnits(0).build())
                                .target(CurrencyAmount.builder().currency("GBP").minorUnits(15000).build())
                                .build()
                })
                .build();

        // when
        Mockito.when(savingsGoalService.getGoals("accountUid")).thenReturn(ResponseEntity.ok(savingsGoalResponse));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/saving-goals/accountUid").accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(savingsGoalResponse));
    }

    @Test
    void testCreateSaving() throws Exception {
        // given
        SavingGoalPayload savingGoalPayload = SavingGoalPayload
                .builder()
                .name("name")
                .currency("GBP")
                .target(CurrencyAmount.builder().currency("GBP").minorUnits(15000).build())
                .build();

        SavingGoalResponse savingGoalResponse = SavingGoalResponse
                .builder()
                .savingsGoalUid("savingsGoalUid")
                .success(true)
                .build();

        // when
        Mockito.when(savingsGoalService.createSavingsGoal(savingGoalPayload, "accountUid"))
                .thenReturn(ResponseEntity.ok(savingGoalResponse));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/saving-goals/accountUid")
                .content(objectMapper.writeValueAsString(savingGoalPayload))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(savingGoalResponse));
    }
}