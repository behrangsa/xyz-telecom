package org.behrang.telecom.properties;

import lombok.Data;
import lombok.Generated;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "pagination")
@Data
@Generated
public class PaginationProperties {

    private int pageSize;

}
