package by.latushka.resourceprocessor.client.impl;

import by.latushka.resourceprocessor.client.MessageListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MessageListenerImpl implements MessageListener {

    @Override
    @RabbitListener(queues = "${message.broker.queue}")
    public void listen(Object message) {
        log.info("Received message: {}", message);
    }
}
