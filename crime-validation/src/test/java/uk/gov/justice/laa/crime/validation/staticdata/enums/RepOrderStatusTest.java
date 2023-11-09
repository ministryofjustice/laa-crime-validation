package uk.gov.justice.laa.crime.validation.staticdata.enums;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class RepOrderStatusTest {

    @Test
    void givenAValidCode_whenGetFromIsInvoked_thenCorrectRepOrderStatusIsReturned() {
        assertThat(RepOrderStatus.getFrom("CURR")).isEqualTo(RepOrderStatus.CURR);
    }

    @Test
    void givenABlankString_whenGetFromIsInvoked_thenNullIsReturned() {
        assertThat(RepOrderStatus.getFrom(null)).isNull();
    }

    @Test
    void givenAnInvalidCode_whenGetFromIsInvoked_thenExceptionIsThrown() {
        assertThatThrownBy(() -> RepOrderStatus.getFrom("INVALID_CODE"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
