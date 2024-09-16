package by.latushka.resourceprocessor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("message.broker")
public class MessageBrokerProperties {
    private String exchange;
    private String queue;
    private String routingKey;
}
