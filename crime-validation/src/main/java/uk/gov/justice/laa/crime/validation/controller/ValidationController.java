package uk.gov.justice.laa.crime.validation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.justice.laa.crime.validation.annotation.DefaultHTTPErrorResponse;
import uk.gov.justice.laa.crime.validation.model.maat_api.ApiIsRoleActionValidRequest;
import uk.gov.justice.laa.crime.validation.service.ValidationService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/internal/v1/validation")
@Tag(name = "Crime Validation", description = "Rest API for Crime Validation.")
public class ValidationController {

    private final ValidationService validationService;

    @PostMapping(value = "/is-user-action-valid", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Check if User Action is Valid")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiIsRoleActionValidRequest.class)
            )
    )
    @DefaultHTTPErrorResponse
    public ResponseEntity<Boolean> isUserActionValid(
            @Parameter(description = "Check if User Action is Valid - Role Action, New Work Reason and Reservation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiIsRoleActionValidRequest.class)
                    )
            ) @Valid @RequestBody ApiIsRoleActionValidRequest request) {
        return ResponseEntity.ok(validationService.isUserActionValid(request));
    }
}
