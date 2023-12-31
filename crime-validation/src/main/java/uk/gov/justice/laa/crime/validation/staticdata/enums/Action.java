package uk.gov.justice.laa.crime.validation.staticdata.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Action {

    UPDATE_APPEAL("UPDATE_APPEAL"),
    RESEND_DRC_DATA("RESEND_DRC DATA"),
    GET_APPLICATION_LIST("GET_APPLICATION_LIST"),
    UPDATE_ASSESSMENT("UPDATE_ASSESSMENT"),
    UPDATE_IOJ("UPDATE_IOJ"),
    UPDATE_CROWN_COURT("UPDATE_CROWN_COURT"),
    UPDATE_LSC_TRANSFER("UPDATE_LSC_TRANSFER"),
    COMPLETE_AREA_TRANSFER("COMPLETE_AREA_TRANSFER"),
    GET_APPLICANTS_LIST("GET_APPLICANTS_LIST"),
    MANAGE_PASSPORT_EVIDENCE("MANAGE_PASSPORT_EVIDENCE"),
    MANAGE_INCOME_EVIDENCE("MANAGE_INCOME_EVIDENCE"),
    CREATE_PASSPORT_ASSESSMENT("CREATE_PASSPORT_ASSESSMENT"),
    CREATE_CORRESPONDENCE("CREATE_CORRESPONDENCE"),
    CREATE_AREA_TRANSFER("CREATE_AREA_TRANSFER"),
    GET_INCOME_RECOGNITION("GET_INCOME_RECOGNITION"),
    UPDATE_APPLICATION("UPDATE_APPLICATION"),
    CREATE_ASSESSMENT("CREATE_ASSESSMENT"),
    CREATE_LSC_TRANSFER("CREATE_LSC_TRANSFER"),
    CREATE_APPLICATION("CREATE_APPLICATION"),
    GET_APPLICANT("GET_APPLICANT"),
    GET_APPLICATION("GET_APPLICATION"),
    CREATE_CROWN_HARDSHIP("CREATE_CROWN_HARDSHIP"),
    UPDATE_CROWN_HARDSHIP("UPDATE_CROWN_HARDSHIP"),
    GET_DRC_DATA("GET_DRC DATA"),
    UPDATE_PASSPORT_ASSESSMENT("UPDATE_PASSPORT_ASSESSMENT"),
    UPDATE_MAGS_HARDSHIP("UPDATE_MAGS_HARDSHIP"),
    CREATE_IOJ("CREATE_IOJ"),
    CREATE_MAGS_HARDSHIP("CREATE_MAGS_HARDSHIP"),
    UPDATE_CAPITAL_AND_EQUITY("UPDATE_CAPITAL_AND_EQUITY");


    private final String code;

    public static Action getFrom(String code) {
        if (StringUtils.isBlank(code)) return null;

        return Stream.of(Action.values())
                .filter(repOrderStatus -> repOrderStatus.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Role Action with value: %s does not exist.", code)));
    }
}
