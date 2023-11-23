package uk.gov.justice.laa.crime.validation.builder;

import org.springframework.stereotype.Component;
import uk.gov.justice.laa.crime.validation.dto.ReservationsDTO;
import uk.gov.justice.laa.crime.validation.dto.UserSummaryDTO;
import uk.gov.justice.laa.crime.validation.model.maat_api.ApiIsRoleActionValidRequest;
import uk.gov.justice.laa.crime.validation.staticdata.enums.Action;
import uk.gov.justice.laa.crime.validation.staticdata.enums.NewWorkReason;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class TestModelDataBuilder {

    public static final String TEST_USER_NAME = "test-s";
    public static final List<String> TEST_ROLE_ACTIONS = List.of("CREATE_ASSESSMENT");
    public static final Action TEST_ACTION = Action.CREATE_ASSESSMENT;
    public static final NewWorkReason TEST_NEW_WORK_REASON = NewWorkReason.CFC;

    public static final List<String> TEST_NEW_WORK_REASONS = List.of("CFC");
    public static final String TEST_USER_SESSION = "sessionId_e5712593c198";
    public static final Integer TEST_RECORD_ID = 100;
    public static final LocalDateTime RESERVATION_DATE = LocalDateTime.of(2022, 12, 14, 0, 0, 0);

    public static UserSummaryDTO getUserSummaryDTO() {
        return UserSummaryDTO.builder()
                .username(TEST_USER_NAME)
                .roleActions(TEST_ROLE_ACTIONS)
                .newWorkReasons(TEST_NEW_WORK_REASONS)
                .reservationsEntity(getReservationsDTO())
                .build();
    }

    public static ReservationsDTO getReservationsDTO() {
        return ReservationsDTO.builder()
                .recordId(TEST_RECORD_ID)
                .recordName("")
                .userName(TEST_USER_NAME)
                .userSession(TEST_USER_SESSION)
                .reservationDate(RESERVATION_DATE)
                .expiryDate(RESERVATION_DATE)
                .build();
    }

    public static ApiIsRoleActionValidRequest getApiIsRoleActionValidRequest() {
        return new ApiIsRoleActionValidRequest()
                .withUsername(TEST_USER_NAME)
                .withAction(TEST_ACTION)
                .withNewWorkReason(TEST_NEW_WORK_REASON)
                .withSessionId("");
    }

    public static ApiIsRoleActionValidRequest getApiIsRoleActionValidRequestWithReservation() {
        return new ApiIsRoleActionValidRequest()
                .withUsername(TEST_USER_NAME)
                .withAction(TEST_ACTION)
                .withNewWorkReason(TEST_NEW_WORK_REASON)
                .withSessionId(TEST_USER_SESSION);
    }
}
