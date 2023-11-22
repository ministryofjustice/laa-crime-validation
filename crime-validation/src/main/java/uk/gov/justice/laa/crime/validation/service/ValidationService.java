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
        UserSummaryDTO userSummaryDTO = maatCourtDataService.getUserSummary(request.getUsername());
        if (request.getAction() == null && request.getNewWorkReason() == null && userSummaryDTO.getReservationsEntity() == null) {
            throw new IllegalArgumentException("Action, New work reason and Reservation Entity does not exist");
        }
        if (request.getAction() != null) {
            if (userSummaryDTO.getRoleActions() != null && !userSummaryDTO.getRoleActions().contains(request.getAction().getCode())) {
                crimeValidationExceptionList.add("User does not have a role capable of performing this action");
            }
        }
        if (request.getNewWorkReason() != null) {
            if (userSummaryDTO.getNewWorkReasons() != null && !userSummaryDTO.getNewWorkReasons().contains(request.getNewWorkReason().getCode())) {
                crimeValidationExceptionList.add("User does not have a valid New Work Reason Code");
            }
        }
        String session = request.getSessionId();
        if (session != null) {
            if (userSummaryDTO.getReservationsEntity() != null && userSummaryDTO.getReservationsEntity().getUserSession().equalsIgnoreCase(session)) {
                crimeValidationExceptionList.add("User have an existing reservation, so reservation not allowed");
            }
        }
        if (crimeValidationExceptionList.isEmpty()) {
            return true;
        } else {
            throw new CrimeValidationException(crimeValidationExceptionList);
        }
    }
}
