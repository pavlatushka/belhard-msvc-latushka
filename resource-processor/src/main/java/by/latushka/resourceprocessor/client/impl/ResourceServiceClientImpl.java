package by.latushka.resourceprocessor.client.impl;

import by.latushka.resourceprocessor.client.ResourceServiceClient;
import by.latushka.resourceprocessor.config.ResourceServiceClientProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@RequiredArgsConstructor
@Component
public class ResourceServiceClientImpl implements ResourceServiceClient {

    private final WebClient webClient;
    private final ResourceServiceClientProperties properties;
    private final LoadBalancerClient loadBalancerClient;

    @Override
    public byte[] getResource(String id) {
        ServiceInstance instance = loadBalancerClient.choose(properties.getServiceId());
        log.info("Choose instance {} of {} with LoadBalancerClient", instance.getInstanceId(), instance.getServiceId());

        byte[] data = webClient.get()
                .uri(uri -> uri
                        .scheme(properties.getScheme())
                        .host(instance.getHost())
                        .port(instance.getPort())
                        .path(properties.getEndpoint())
                        .build(id))
                .retrieve()
                .bodyToMono(byte[].class)
                .block();

        return data;
    }
}
