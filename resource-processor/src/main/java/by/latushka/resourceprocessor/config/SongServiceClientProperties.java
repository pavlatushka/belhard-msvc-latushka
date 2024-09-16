package by.latushka.resourceprocessor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("song.service.client")
public class SongServiceClientProperties {
    private String scheme;
    private String serviceId;
    private String endpoint;
}
