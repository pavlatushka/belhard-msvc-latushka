package by.latushka.resourceservice.client.impl;

import by.latushka.resourceservice.client.SongServiceClient;
import by.latushka.resourceservice.config.SongServiceClientProperties;
import by.latushka.resourceservice.dto.Mp3FileMetadata;
import by.latushka.resourceservice.dto.SongServiceClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@RequiredArgsConstructor
@Component
public class SongServiceClientImpl implements SongServiceClient {
    private final WebClient webClient;
    private final SongServiceClientProperties properties;

    @Override
    public void saveMetadata(Mp3FileMetadata metadata) throws SongServiceClientException {
        try {
            log.debug("Sending request to save metadata in Song Service: {}", metadata);
            webClient.post()
                    .uri(uri -> uri.scheme(properties.getScheme())
                            .host(properties.getHost())
                            .port(properties.getPort())
                            .path(properties.getEndpoint())
                            .build())
                    .bodyValue(metadata)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        } catch (Exception e) {
            log.error("Failed to send request to Song Service", e);
            throw new SongServiceClientException(e);
        }
    }
}
