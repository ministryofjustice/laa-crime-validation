package uk.gov.justice.laa.crime.validation.staticdata.enums;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class NewWorkReasonTest {

    @Test
    void givenValidResultString_whenGetFromIsInvoked_thenCorrectEnumIsReturned() {
        assertThat(NewWorkReason.getFrom("FMA")).isEqualTo(NewWorkReason.FMA);
    }

    @Test
    void givenBlankString_whenGetFromIsInvoked_thenNullIsReturned() {
        assertThat(NewWorkReason.getFrom(null)).isNull();
    }

    @Test
    void givenInvalidResultString_whenGetFromIsInvoked_thenExceptionIsThrown() {
        assertThatThrownBy(
                () -> NewWorkReason.getFrom("MOCK_RESULT_STRING")
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenValidInput_ValidateEnumValues() {
        assertThat("FMA").isEqualTo(NewWorkReason.FMA.getCode());
        assertThat("ASS").isEqualTo(NewWorkReason.FMA.getType());
        assertThat("First Means Assessment").isEqualTo(NewWorkReason.FMA.getDescription());
    }
}
