package uk.gov.justice.laa.crime.validation.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.justice.laa.crime.validation.CrimeValidationApplication;
import uk.gov.justice.laa.crime.validation.builder.TestModelDataBuilder;
import uk.gov.justice.laa.crime.validation.config.CrimeValidationTestConfiguration;
import uk.gov.justice.laa.crime.validation.dto.UserSummaryDTO;
import uk.gov.justice.laa.crime.validation.model.maat_api.ApiIsRoleActionValidRequest;

import java.util.Map;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.justice.laa.crime.validation.util.RequestBuilderUtils.buildRequestGivenContent;


@DirtiesContext
@Import(CrimeValidationTestConfiguration.class)
@SpringBootTest(classes = CrimeValidationApplication.class, webEnvironment = DEFINED_PORT)
@AutoConfigureWireMock(port = 9999)
class ValidationIntegrationTest {

    private static final String ENDPOINT_URL_IS_USER_ACTION_VALID = "/api/internal/v1/validation/is-user-action-valid";
    private MockMvc mvc;
    @Autowired
    private WireMockServer wiremock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @AfterEach
    void after() {
        wiremock.resetAll();
    }

    @BeforeEach
    public void setup() throws JsonProcessingException {
        stubForOAuth();
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .addFilter(springSecurityFilterChain).build();
    }


    @Test
    void givenAEmptyContent_whenIsUserActionValidIsInvoked_thenFailsWithBadRequest() throws Exception {
        mvc.perform(buildRequestGivenContent(HttpMethod.POST, "{}", ENDPOINT_URL_IS_USER_ACTION_VALID))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenAEmptyOAuthToken_whenIsUserActionValidIsInvoked_thenFailsWithUnauthorizedAccess() throws Exception {
        mvc.perform(buildRequestGivenContent(HttpMethod.POST, "{}", ENDPOINT_URL_IS_USER_ACTION_VALID, false))
                .andExpect(status().isUnauthorized()).andReturn();
    }

    @Test
    void givenValidRequest_whenIsUserActionValidIsInvoked_thenCorrectResponseIsReturned() throws Exception {
        ApiIsRoleActionValidRequest request = TestModelDataBuilder.getApiIsRoleActionValidRequest();
        String requestBody = objectMapper.writeValueAsString(request);

        UserSummaryDTO response = TestModelDataBuilder.getUserSummaryDTO();

        wiremock.stubFor(get(urlEqualTo("/api/internal/v1/users/summary/" + TestModelDataBuilder.TEST_USER_NAME)).willReturn(
                WireMock.ok()
                        .withHeader("Content-Type", String.valueOf(MediaType.APPLICATION_JSON))
                        .withBody(objectMapper.writeValueAsString(response))));

        mvc.perform(buildRequestGivenContent(HttpMethod.POST, requestBody, ENDPOINT_URL_IS_USER_ACTION_VALID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
        verify(exactly(1), postRequestedFor(urlEqualTo("/api/internal/v1/users/summary/" + TestModelDataBuilder.TEST_USER_NAME)));
    }


    @Test
    void givenInvalidRequest_whenCreateHardshipIsInvoked_thenFailsWithBadRequest() throws Exception {
        ApiIsRoleActionValidRequest request = TestModelDataBuilder.getApiIsRoleActionValidRequest();
        request.setAction(null);
        request.setNewWorkReason(null);
        request.setSessionId(null);
        String requestBody = objectMapper.writeValueAsString(request);

        mvc.perform(buildRequestGivenContent(HttpMethod.POST, requestBody, ENDPOINT_URL_IS_USER_ACTION_VALID))
                .andExpect(status().isBadRequest());
    }


    private void stubForOAuth() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> token = Map.of(
                "expires_in", 3600,
                "token_type", "Bearer",
                "access_token", UUID.randomUUID()
        );

        wiremock.stubFor(
                post("/oauth2/token").willReturn(
                        WireMock.ok()
                                .withHeader("Content-Type", String.valueOf(MediaType.APPLICATION_JSON))
                                .withBody(mapper.writeValueAsString(token))
                )
        );
    }


}