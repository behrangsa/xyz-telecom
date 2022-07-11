package org.behrang.telecom.properties;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "repositories.phone-numbers")
@Component
@RequiredArgsConstructor
@Data
public class PhoneNumberRepositoryProperties {

    private Queries queries;

    @Data
    public static class Queries {
        private String findAllPhoneNumbers;
    }

}
