package uk.gov.justice.laa.crime.validation.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uk.gov.justice.laa.crime.validation.dto.ErrorDTO;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class CrimeValidationExceptionHandler {

    private static ResponseEntity<ErrorDTO> buildErrorResponse(HttpStatus status, List<String> errorMessage) {
        return new ResponseEntity<>(ErrorDTO.builder().code(status.toString()).messageList(errorMessage).build(), status);
    }

    @ExceptionHandler(CrimeValidationException.class)
    public ResponseEntity<ErrorDTO> handleCrimeValidationException(CrimeValidationException ex) {
        log.error("CrimeValidationException: ", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getExceptionMessage().stream().collect(Collectors.toList()));
    }

}
