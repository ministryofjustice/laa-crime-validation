package uk.gov.justice.laa.crime.validation.service;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.justice.laa.crime.validation.builder.TestModelDataBuilder;
import uk.gov.justice.laa.crime.validation.dto.UserSummaryDTO;
import uk.gov.justice.laa.crime.validation.exception.CrimeValidationException;
import uk.gov.justice.laa.crime.validation.model.maat_api.ApiIsRoleActionValidRequest;
import uk.gov.justice.laa.crime.validation.staticdata.enums.Action;
import uk.gov.justice.laa.crime.validation.staticdata.enums.NewWorkReason;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SoftAssertionsExtension.class)
class ValidationServiceTest {

    @InjectMocks
    private ValidationService validationService;

    @Mock
    private MaatCourtDataService maatCourtDataService;

    @Test
    void givenValidParameters_whenIsUserActionValidIsInvoked_thenValidationStatusIsReturned() {
        UserSummaryDTO expectedUserSummaryDTO = TestModelDataBuilder.getUserSummaryDTO();
        when(maatCourtDataService.getUserSummary(any())).thenReturn(expectedUserSummaryDTO);
        Boolean isUserActionValid =
                validationService.isUserActionValid(TestModelDataBuilder.getApiIsRoleActionValidRequest());
        assertTrue(isUserActionValid);
    }


    @Test
    void givenInputWithExistingReservation_whenIsUserActionValidIsInvoked_thenExceptionIsThrown() throws CrimeValidationException {
        UserSummaryDTO expectedUserSummaryDTO = TestModelDataBuilder.getUserSummaryDTO();
        when(maatCourtDataService.getUserSummary(any())).thenReturn(expectedUserSummaryDTO);
        assertThatThrownBy(() -> validationService.isUserActionValid(TestModelDataBuilder.getApiIsRoleActionValidRequestWithReservation()))
                .isInstanceOf(CrimeValidationException.class)
                .extracting("exceptionMessage", InstanceOfAssertFactories.ITERABLE)
                .contains("User have an existing reservation, so reservation not allowed");
    }

    @Test
    void givenInputWithRoleActionNotAssignedToUser_whenIsUserActionValidIsInvoked_thenExceptionIsThrown() throws CrimeValidationException {
        UserSummaryDTO expectedUserSummaryDTO = TestModelDataBuilder.getUserSummaryDTO();
        when(maatCourtDataService.getUserSummary(any())).thenReturn(expectedUserSummaryDTO);
        ApiIsRoleActionValidRequest apiIsRoleActionValidRequest = TestModelDataBuilder.getApiIsRoleActionValidRequest();
        apiIsRoleActionValidRequest.setAction(Action.CREATE_APPLICATION);
        assertThatThrownBy(() -> validationService.isUserActionValid(apiIsRoleActionValidRequest))
                .isInstanceOf(CrimeValidationException.class)
                .extracting("exceptionMessage", InstanceOfAssertFactories.ITERABLE)
                .contains("User does not have a role capable of performing this action");

    }

    @Test
    void givenInputWithNewWorkReasonNotAssignedToUser_whenIsUserActionValidIsInvoked_thenExceptionIsThrown() throws CrimeValidationException {
        UserSummaryDTO expectedUserSummaryDTO = TestModelDataBuilder.getUserSummaryDTO();
        when(maatCourtDataService.getUserSummary(any())).thenReturn(expectedUserSummaryDTO);
        ApiIsRoleActionValidRequest apiIsRoleActionValidRequest = TestModelDataBuilder.getApiIsRoleActionValidRequest();
        apiIsRoleActionValidRequest.setNewWorkReason(NewWorkReason.NEW);
        assertThatThrownBy(() -> validationService.isUserActionValid(apiIsRoleActionValidRequest))
                .isInstanceOf(CrimeValidationException.class)
                .extracting("exceptionMessage", InstanceOfAssertFactories.ITERABLE)
                .contains("User does not have a valid New Work Reason Code");
    }

    @Test
    void givenParametersWithNoExistingReservation_whenIsUserActionValidIsInvoked_thenValidationStatusIsReturned() throws CrimeValidationException {
        UserSummaryDTO expectedUserSummaryDTO = TestModelDataBuilder.getUserSummaryDTO();
        when(maatCourtDataService.getUserSummary(any())).thenReturn(expectedUserSummaryDTO);
        ApiIsRoleActionValidRequest apiIsRoleActionValidRequest = TestModelDataBuilder.getApiIsRoleActionValidRequest();
        apiIsRoleActionValidRequest.setSessionId("");
        Boolean isUserActionValid =
                validationService.isUserActionValid(TestModelDataBuilder.getApiIsRoleActionValidRequest());
        assertTrue(isUserActionValid);
    }

    @Test
    void givenInputWithRoleActionNotAssignedAndNewWorkReasonNotAssignedAndWithExistingReservation_whenIsUserActionValidIsInvoked_thenExceptionIsThrown() throws CrimeValidationException {
        UserSummaryDTO expectedUserSummaryDTO = TestModelDataBuilder.getUserSummaryDTO();
        when(maatCourtDataService.getUserSummary(any())).thenReturn(expectedUserSummaryDTO);

        ApiIsRoleActionValidRequest apiIsRoleActionValidRequest = TestModelDataBuilder.getApiIsRoleActionValidRequestWithReservation();
        apiIsRoleActionValidRequest.setAction(Action.CREATE_APPLICATION);
        apiIsRoleActionValidRequest.setNewWorkReason(NewWorkReason.NEW);

        assertThatThrownBy(() -> validationService.isUserActionValid(apiIsRoleActionValidRequest))
        .isInstanceOf(CrimeValidationException.class)
        .extracting("exceptionMessage", InstanceOfAssertFactories.ITERABLE)
        .contains("User does not have a valid New Work Reason Code",
                "User does not have a role capable of performing this action",
                "User have an existing reservation, so reservation not allowed"
        );
    }

    @Test
    void givenInputWithRoleActionNotAssignedAndNewWorkReasonNotAssigned_whenIsUserActionValidIsInvoked_thenExceptionIsThrown() throws CrimeValidationException {
        UserSummaryDTO expectedUserSummaryDTO = TestModelDataBuilder.getUserSummaryDTO();
        when(maatCourtDataService.getUserSummary(any())).thenReturn(expectedUserSummaryDTO);

        ApiIsRoleActionValidRequest apiIsRoleActionValidRequest = TestModelDataBuilder.getApiIsRoleActionValidRequestWithReservation();
        apiIsRoleActionValidRequest.setAction(Action.CREATE_APPLICATION);
        apiIsRoleActionValidRequest.setNewWorkReason(NewWorkReason.NEW);
        apiIsRoleActionValidRequest.setSessionId("");

        assertThatThrownBy(() -> validationService.isUserActionValid(apiIsRoleActionValidRequest))
                .isInstanceOf(CrimeValidationException.class)
                .extracting("exceptionMessage", InstanceOfAssertFactories.ITERABLE)
                .contains("User does not have a valid New Work Reason Code",
                        "User does not have a role capable of performing this action"
                );
    }

    @Test
    void givenInputWithRoleActionNotAssignedAndWithExistingReservation_whenIsUserActionValidIsInvoked_thenExceptionIsThrown() throws CrimeValidationException {
        UserSummaryDTO expectedUserSummaryDTO = TestModelDataBuilder.getUserSummaryDTO();
        when(maatCourtDataService.getUserSummary(any())).thenReturn(expectedUserSummaryDTO);

        ApiIsRoleActionValidRequest apiIsRoleActionValidRequest = TestModelDataBuilder.getApiIsRoleActionValidRequestWithReservation();
        apiIsRoleActionValidRequest.setAction(Action.CREATE_APPLICATION);

        assertThatThrownBy(() -> validationService.isUserActionValid(apiIsRoleActionValidRequest))
                .isInstanceOf(CrimeValidationException.class)
                .extracting("exceptionMessage", InstanceOfAssertFactories.ITERABLE)
                .contains("User does not have a role capable of performing this action",
                        "User have an existing reservation, so reservation not allowed");
    }

    @Test
    void givenInputWithNewWorkReasonNotAssignedAndWithExistingReservation_whenIsUserActionValidIsInvoked_thenExceptionIsThrown() throws CrimeValidationException {
        UserSummaryDTO expectedUserSummaryDTO = TestModelDataBuilder.getUserSummaryDTO();
        when(maatCourtDataService.getUserSummary(any())).thenReturn(expectedUserSummaryDTO);

        ApiIsRoleActionValidRequest apiIsRoleActionValidRequest = TestModelDataBuilder.getApiIsRoleActionValidRequestWithReservation();
        apiIsRoleActionValidRequest.setNewWorkReason(NewWorkReason.NEW);

        assertThatThrownBy(() -> validationService.isUserActionValid(apiIsRoleActionValidRequest))
                .isInstanceOf(CrimeValidationException.class)
                .extracting("exceptionMessage", InstanceOfAssertFactories.ITERABLE)
                .contains("User does not have a valid New Work Reason Code",
                        "User have an existing reservation, so reservation not allowed");
    }

    @Test
    void givenInvalidInput_whenIsUserActionValidIsInvoked_thenExceptionIsThrown() throws CrimeValidationException {
        UserSummaryDTO expectedUserSummaryDTO = TestModelDataBuilder.getUserSummaryDTO();
        when(maatCourtDataService.getUserSummary(any())).thenReturn(expectedUserSummaryDTO);

        ApiIsRoleActionValidRequest apiIsRoleActionValidRequest = TestModelDataBuilder.getApiIsRoleActionValidRequestWithReservation();
        apiIsRoleActionValidRequest.setNewWorkReason(null);
        apiIsRoleActionValidRequest.setAction(null);
        apiIsRoleActionValidRequest.setSessionId(null);

        assertThatThrownBy(() -> validationService.isUserActionValid(apiIsRoleActionValidRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Action, New work reason and Session does not exist");
    }
}
