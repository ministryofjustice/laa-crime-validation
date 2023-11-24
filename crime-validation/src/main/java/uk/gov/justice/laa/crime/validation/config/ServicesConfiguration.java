package uk.gov.justice.laa.crime.validation.config;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Data
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "services")
@Service
public class ServicesConfiguration {

    @NotNull
    private MaatApi maatApi;

    @NotNull
    private boolean oAuthEnabled;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MaatApi {

        @NotNull
        private String baseUrl;

        @NotNull
        private UserEndpoints userEndpoints;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class UserEndpoints {
            
            @NotNull
            private String userSummaryUrl;
        }
    }

}
