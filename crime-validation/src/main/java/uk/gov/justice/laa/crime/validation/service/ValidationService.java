package uk.gov.justice.laa.crime.validation.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.gov.justice.laa.crime.validation.dto.UserSummaryDTO;
import uk.gov.justice.laa.crime.validation.exception.CrimeValidationException;
import uk.gov.justice.laa.crime.validation.model.maat_api.ApiIsRoleActionValidRequest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationService {

    private final MaatCourtDataService maatCourtDataService;

    public Boolean isUserActionValid(@NonNull ApiIsRoleActionValidRequest request) {
        List<String> crimeValidationExceptionList = new ArrayList<>();

        if (request.getAction() == null && request.getNewWorkReason() == null && request.getSessionId() == null) {
            throw new IllegalArgumentException("Action, New work reason and Session does not exist");
        }
        UserSummaryDTO userSummaryDTO = maatCourtDataService.getUserSummary(request.getUsername());

        if (request.getAction() != null && (userSummaryDTO.getRoleActions() == null
                || !userSummaryDTO.getRoleActions().contains(request.getAction().getCode()))) {
            crimeValidationExceptionList.add("User does not have a role capable of performing this action");
        }

        if (request.getNewWorkReason() != null && (userSummaryDTO.getNewWorkReasons() == null
                || !userSummaryDTO.getNewWorkReasons().contains(request.getNewWorkReason().getCode()))) {
            crimeValidationExceptionList.add("User does not have a valid New Work Reason Code");
        }

        if (request.getSessionId() != null && (userSummaryDTO.getReservationsEntity() != null
                && userSummaryDTO.getReservationsEntity().getUserSession().equalsIgnoreCase(request.getSessionId()))) {
            crimeValidationExceptionList.add("User have an existing reservation, so reservation not allowed");
        }

        if (!crimeValidationExceptionList.isEmpty()) {
            throw new CrimeValidationException(crimeValidationExceptionList);
        }

        return true;
    }
}
