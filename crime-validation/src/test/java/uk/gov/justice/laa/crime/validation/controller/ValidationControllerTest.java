package uk.gov.justice.laa.crime.validation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.justice.laa.crime.validation.builder.TestModelDataBuilder;
import uk.gov.justice.laa.crime.validation.config.CrimeValidationTestConfiguration;
import uk.gov.justice.laa.crime.validation.exception.CrimeValidationException;
import uk.gov.justice.laa.crime.validation.model.maat_api.ApiIsRoleActionValidRequest;
import uk.gov.justice.laa.crime.validation.service.ValidationService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static uk.gov.justice.laa.crime.validation.util.RequestBuilderUtils.buildRequestGivenContent;

@WebMvcTest(ValidationController.class)
@Import(CrimeValidationTestConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
class ValidationControllerTest {

    private static final String ENDPOINT_URL_IS_USER_ACTION_VALID = "/api/internal/v1/validation/is-user-action-valid";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ValidationService validationService;


    @Test
    void givenValidInput_whenIsUserActionValidIsInvoked_thenOkResponseIsReturned() throws Exception {
        ApiIsRoleActionValidRequest request = TestModelDataBuilder.getApiIsRoleActionValidRequest();
        String requestBody = objectMapper.writeValueAsString(request);

        when(validationService.isUserActionValid(any())).thenReturn(true);

        mvc.perform(buildRequestGivenContent(HttpMethod.POST, requestBody, ENDPOINT_URL_IS_USER_ACTION_VALID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(true));

    }

    @Test
    void givenInvalidRequest_whenIsUserActionValidIsInvoked_thenBadRequestResponseIsReturned() throws Exception {
        mvc.perform(buildRequestGivenContent(HttpMethod.POST, "{}", ENDPOINT_URL_IS_USER_ACTION_VALID))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenFailedApiCall_whenCreateIsInvoked_thenInternalServerErrorResponseIsReturned() throws Exception {
        ApiIsRoleActionValidRequest request = TestModelDataBuilder.getApiIsRoleActionValidRequest();
        when(validationService.isUserActionValid(any()))
                .thenThrow(new CrimeValidationException(List.of("User does not have a role capable of performing this action")));
        String requestBody = objectMapper.writeValueAsString(request);
        mvc.perform(buildRequestGivenContent(HttpMethod.POST, requestBody, ENDPOINT_URL_IS_USER_ACTION_VALID))
                .andExpect(status().isInternalServerError());
    }
}
