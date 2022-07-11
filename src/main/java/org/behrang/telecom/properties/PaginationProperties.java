package org.behrang.telecom.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "pagination")
@Data
public class PaginationProperties {

    private int pageSize;

}
