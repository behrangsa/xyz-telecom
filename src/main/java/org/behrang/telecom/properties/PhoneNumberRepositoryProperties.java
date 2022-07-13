package org.behrang.telecom.properties;

import lombok.Data;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "repositories.phone-numbers")
@Component
@RequiredArgsConstructor
@Data
@Generated
public class PhoneNumberRepositoryProperties {

    private Queries queries;

    @Data
    @Generated
    public static class Queries {
        private String findAllPhoneNumbers;

        private String countAll;

        private String countAllByCustomerId;

        private String findByPhoneNumber;

        private String activateByPhoneNumber;

        private String findAllPhoneNumbersByCustomerId;
    }

}
