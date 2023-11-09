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

    @Test
    void givenAValidRepOrderStatus_validateEnumValues() {
        assertThat("ERR").isEqualTo(RepOrderStatus.ERR.getCode());
        assertThat("Created in Error").isEqualTo(RepOrderStatus.ERR.getDescription());
        assertThat(3).isEqualTo(RepOrderStatus.ERR.getSequence());
        assertThat(RepOrderStatus.ERR.isRemoveContribs()).isTrue();
        assertThat(RepOrderStatus.ERR.isUpdateAllowed()).isFalse();
    }
}
