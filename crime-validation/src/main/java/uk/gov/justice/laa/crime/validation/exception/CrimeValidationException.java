package uk.gov.justice.laa.crime.validation.exception;


import lombok.Data;

import java.util.List;

@Data
public class CrimeValidationException extends RuntimeException {

    private final List<String> exceptionMessage;

    public CrimeValidationException(List<String> exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

}
