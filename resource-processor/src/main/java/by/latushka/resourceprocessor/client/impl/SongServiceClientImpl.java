package by.latushka.resourceprocessor.client.impl;

import by.latushka.resourceprocessor.client.SongServiceClient;
import by.latushka.resourceprocessor.config.SongServiceClientProperties;
import by.latushka.resourceprocessor.dto.MetadataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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
    private final LoadBalancerClient loadBalancerClient;

    @Override
    public void postMetadata(MetadataDto dto) {
        ServiceInstance instance = loadBalancerClient.choose(properties.getServiceId());
        log.info("Choose instance {} of {} with LoadBalancerClient", instance.getInstanceId(), instance.getServiceId());

        Object res = webClient.post()
                .uri(uri -> uri
                        .scheme(properties.getScheme())
                        .host(instance.getHost())
                        .port(instance.getPort())
                        .path(properties.getEndpoint())
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        log.debug("Post song metadata response: {}", res);
    }
}
