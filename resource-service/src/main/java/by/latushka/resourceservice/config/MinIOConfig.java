package by.latushka.resourceservice.config;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MinIOConfig {
    private final MinIOStorageClientConfig cfg;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                        .endpoint(cfg.getEndpoint())
                        .credentials(cfg.getAccessKey(), cfg.getSecretKey())
                        .build();
    }
}
