package by.latushka.resourceprocessor.listener.impl;

import by.latushka.resourceprocessor.client.ResourceServiceClient;
import by.latushka.resourceprocessor.client.SongServiceClient;
import by.latushka.resourceprocessor.dto.MetadataDto;
import by.latushka.resourceprocessor.listener.MessageListener;
import by.latushka.resourceprocessor.service.MetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class MessageListenerImpl implements MessageListener {
    private final ResourceServiceClient resourceServiceClient;
    private final SongServiceClient songServiceClient;
    private final MetadataService metadataService;

    @Override
    @RabbitListener(queues = "${message.broker.queue}")
    public void listenToUpload(String id) {
        log.info("Received uploaded resource id from the queue: {}", id);

        byte[] rawSong = resourceServiceClient.getResource(id);
        MetadataDto metadata = metadataService.parseMetadata(rawSong);

        metadata.setResourceId(Long.valueOf(id));
        songServiceClient.postMetadata(metadata);
    }
}
