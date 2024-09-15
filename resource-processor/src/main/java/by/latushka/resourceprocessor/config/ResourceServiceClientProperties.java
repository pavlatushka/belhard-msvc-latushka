package by.latushka.resourceprocessor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("resource.service.client")
public class ResourceServiceClientProperties {
    private String scheme;
    private String serviceId;
    private String endpoint;
}
