package by.latushka.resourceservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("minio")
public class MinIOStorageClientConfig {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
