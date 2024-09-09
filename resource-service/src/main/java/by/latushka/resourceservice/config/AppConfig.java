package by.latushka.resourceservice.config;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final MinIOStorageClientConfig cfg;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                        .endpoint(cfg.getEndpoint())
                        .credentials(cfg.getAccessKey(), cfg.getSecretKey())
                        .build();
    }
}
