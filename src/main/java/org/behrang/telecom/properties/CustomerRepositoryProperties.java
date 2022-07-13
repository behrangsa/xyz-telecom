package org.behrang.telecom.properties;

import lombok.Data;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "repositories.customers")
@Component
@RequiredArgsConstructor
@Data
@Generated
public class CustomerRepositoryProperties {

    private Queries queries;

    @Data
    @Generated
    public static class Queries {
        private String findById;
    }

}
