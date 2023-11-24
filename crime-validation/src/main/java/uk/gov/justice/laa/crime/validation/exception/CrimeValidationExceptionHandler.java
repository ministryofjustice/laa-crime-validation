package uk.gov.justice.laa.crime.validation.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uk.gov.justice.laa.crime.commons.tracing.TraceIdHandler;
import uk.gov.justice.laa.crime.validation.dto.ErrorDTO;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class CrimeValidationExceptionHandler {

    private final TraceIdHandler traceIdHandler;

    private static ResponseEntity<ErrorDTO> buildErrorResponse(HttpStatus status, List<String> errorMessage) {
        return new ResponseEntity<>(ErrorDTO.builder().code(status.toString()).messageList(errorMessage).build(), status);
    }

    private static ResponseEntity<ErrorDTO> buildErrorResponseString(HttpStatus status, String message, String traceId) {
        return new ResponseEntity<>(ErrorDTO.builder().traceId(traceId).code(status.toString()).message(message).build(), status);
    }
    @ExceptionHandler(CrimeValidationException.class)
    public ResponseEntity<ErrorDTO> handleCrimeValidationException(CrimeValidationException ex) {
        log.error("CrimeValidationException: ", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getExceptionMessage().stream().collect(Collectors.toList()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("CrimeValidationException: ", ex);
        return buildErrorResponseString(HttpStatus.BAD_REQUEST, ex.getMessage(), traceIdHandler.getTraceId());
    }

}
