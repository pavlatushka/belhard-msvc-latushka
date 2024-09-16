package by.latushka.songservice.listener.impl;

import by.latushka.songservice.listener.MessageListener;
import by.latushka.songservice.service.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Log4j2
@RequiredArgsConstructor
public class MessageListenerImpl implements MessageListener {
    private final SongService songService;

    @Override
    @RabbitListener(queues = "${message.broker.queue}")
    public void listenToDelete(Set<Long> ids) {
        log.info("Received deleted resource id from the queue: {}", ids);

        songService.deleteAllByResourceId(ids);
    }
}
