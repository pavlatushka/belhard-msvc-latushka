package by.latushka.resourceservice.client.impl;

import by.latushka.resourceservice.client.MessagePublisher;
import by.latushka.resourceservice.config.MessageBrokerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class MessagePublisherImpl implements MessagePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final MessageBrokerProperties properties;

    @Override
    public void postMessage(Object message) {
        log.info("Sending message: {}", message);
        rabbitTemplate.convertAndSend(properties.getExchange(), properties.getRoutingKey(), message);
    }
}
