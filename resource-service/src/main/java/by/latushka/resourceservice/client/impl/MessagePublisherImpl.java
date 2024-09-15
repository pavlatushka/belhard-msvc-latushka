package by.latushka.resourceservice.client.impl;

import by.latushka.resourceservice.client.MessagePublisher;
import by.latushka.resourceservice.config.MessageBrokerProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MessagePublisherImpl implements MessagePublisher {
    private final RabbitTemplate rabbitTemplate;
    private final MessageBrokerProperties properties;
    private static final String routingPostfix = "1";

    public MessagePublisherImpl(RabbitTemplate rabbitTemplate, MessageBrokerProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    @Override
    public void uploadMessage(Object message) {
        log.info("Sending upload message: {}", message);
        rabbitTemplate.convertAndSend(properties.getExchange(), properties.getUploadRoutingKey() + routingPostfix, message);
    }

    @Override
    public void deleteMessage(Object message) {
        log.info("Sending delete message: {}", message);
        rabbitTemplate.convertAndSend(properties.getExchange(), properties.getDeleteRoutingKey() + routingPostfix, message);
    }
}
