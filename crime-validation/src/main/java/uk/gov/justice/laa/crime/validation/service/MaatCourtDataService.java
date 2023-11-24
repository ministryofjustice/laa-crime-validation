package uk.gov.justice.laa.crime.validation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import uk.gov.justice.laa.crime.commons.client.RestAPIClient;
import uk.gov.justice.laa.crime.validation.config.ServicesConfiguration;
import uk.gov.justice.laa.crime.validation.dto.UserSummaryDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaatCourtDataService {
    private static final String RESPONSE_STRING = "Response from Court Data API: %s";
    @Qualifier("maatApiClient")
    private final RestAPIClient maatAPIClient;
    private final ServicesConfiguration configuration;

    public UserSummaryDTO getUserSummary(String username) {
        UserSummaryDTO userSummaryDTO = maatAPIClient.get(
                new ParameterizedTypeReference<>() {
                },
                configuration.getMaatApi().getUserEndpoints().getUserSummaryUrl(),
                username
        );

        log.info(String.format(RESPONSE_STRING, userSummaryDTO));
        return userSummaryDTO;
    }
}
