package by.latushka.resourceservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("song.service.client")
public class SongServiceClientProperties {
    private String scheme;
    private String host;
    private String port;
    private String endpoint;
}
