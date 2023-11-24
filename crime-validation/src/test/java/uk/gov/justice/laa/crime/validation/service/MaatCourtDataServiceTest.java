package uk.gov.justice.laa.crime.validation.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.justice.laa.crime.commons.client.RestAPIClient;
import uk.gov.justice.laa.crime.validation.builder.TestModelDataBuilder;
import uk.gov.justice.laa.crime.validation.config.MockServicesConfiguration;
import uk.gov.justice.laa.crime.validation.config.ServicesConfiguration;
import uk.gov.justice.laa.crime.validation.dto.UserSummaryDTO;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaatCourtDataServiceTest {

    @Mock
    private RestAPIClient maatCourtDataClient;

    @InjectMocks
    private MaatCourtDataService maatCourtDataService;

    @Spy
    private ServicesConfiguration configuration = MockServicesConfiguration.getConfiguration(1000);

    @Test
    void givenAValidUsername_whenGetUserSummaryIsInvoked_thenResponseIsReturned() {
        UserSummaryDTO expected = TestModelDataBuilder.getUserSummaryDTO();
        when(maatCourtDataClient.get(any(), anyString(), anyString()))
                .thenReturn(expected);
        maatCourtDataService.getUserSummary(TestModelDataBuilder.TEST_USER_NAME);
        verify(maatCourtDataClient).get(any(), anyString(), anyString());
    }
}