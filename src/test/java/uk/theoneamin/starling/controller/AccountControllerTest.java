package uk.theoneamin.starling.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.theoneamin.starling.exception.GlobalExceptionHandler;
import uk.theoneamin.starling.model.requests.accounts.AccountResponse;
import uk.theoneamin.starling.model.requests.accounts.MainAccountsResponse;
import uk.theoneamin.starling.service.AccountService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    private MockMvc mvc;
    private JacksonTester<MainAccountsResponse> mainAccountsResponseJacksonTester;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetAccounts() throws Exception {
        // given
        AccountResponse accountResponse = AccountResponse
                .builder()
                .accountType("PRIMARY")
                .accountUid("88n88n88n-88n8-88n8-88n8-88n88n88n88n")
                .defaultCategory("defaultCategory")
                .currency("currency")
                .createdAt("2021-08-01T00:00:00.000Z")
                .name("name")
                .build();

        MainAccountsResponse mainAccountsResponse = MainAccountsResponse
                .builder()
                .accounts(new AccountResponse[]{accountResponse})
                .build();

        // when
        Mockito.when(accountService.getAccounts()).thenReturn(ResponseEntity.ok(mainAccountsResponse));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/accounts").accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder)
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                mainAccountsResponseJacksonTester.write(mainAccountsResponse).getJson()
        );
    }

    @Test
    void testRefreshAccounts() throws Exception {
        // given
        AccountResponse accountResponse = AccountResponse
                .builder()
                .accountType("PRIMARY")
                .accountUid("88n88n88n-88n8-88n8-88n8-88n88n88n88n")
                .defaultCategory("defaultCategory")
                .currency("currency")
                .createdAt("2021-08-01T00:00:00.000Z")
                .name("name")
                .build();

        MainAccountsResponse mainAccountsResponse = MainAccountsResponse
                .builder()
                .accounts(new AccountResponse[]{accountResponse})
                .build();

        // when
        Mockito.when(accountService.refreshAccounts()).thenReturn(ResponseEntity.ok(mainAccountsResponse));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/accounts/refresh").accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder)
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                mainAccountsResponseJacksonTester.write(mainAccountsResponse).getJson()
        );
    }

    @Test
    void testRefreshAccountsWrongHttpMethod() throws Exception {
        // when
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/accounts/refresh").accept(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder)
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED.value());
    }
}