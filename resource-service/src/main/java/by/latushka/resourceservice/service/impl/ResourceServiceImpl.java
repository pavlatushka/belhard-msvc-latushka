package by.latushka.resourceservice.service.impl;

import by.latushka.resourceservice.client.StorageClient;
import by.latushka.resourceservice.client.MessagePublisher;
import by.latushka.resourceservice.entity.Mp3File;
import by.latushka.resourceservice.exception.InvalidResourceException;
import by.latushka.resourceservice.exception.ResourceNotFoundException;
import by.latushka.resourceservice.repository.Mp3FileRepository;
import by.latushka.resourceservice.service.ResourceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class ResourceServiceImpl implements ResourceService {
    private final Mp3FileRepository mp3FileRepository;
    private final StorageClient storageClient;
    private final MessagePublisher messagePublisher;

    @Override
    public byte[] findById(Long id) throws ResourceNotFoundException {
        Optional<Mp3File> mp3File = mp3FileRepository.findById(id);
        if(mp3File.isEmpty()) {
            log.info("Resource with id {} not found", id);
            throw new ResourceNotFoundException();
        }

        return storageClient.get(mp3File.get().getResourceId());
    }

    @Override
    public Long upload(InputStream is) throws InvalidResourceException {
        byte[] rawData;
        try {
            rawData = is.readAllBytes();
        } catch (IOException e) {
            throw new InvalidResourceException(e);
        }

        String resourceId = UUID.randomUUID().toString();

        storageClient.put(resourceId, rawData);
        Long mp3FileId = save(resourceId);

        if (mp3FileId != null) {
            messagePublisher.uploadMessage(mp3FileId);
        }
        return mp3FileId;
    }

    @SneakyThrows
    @Override
    @Transactional
    public Set<Long> deleteAll(Set<Long> ids) {
        if(ids == null || ids.isEmpty()) {
            return Set.of();
        }
        Set<Mp3File> files = mp3FileRepository.findExisting(ids);
        files.forEach(f -> storageClient.delete(f.getResourceId()));

        Set<Long> existingIds = files.stream().map(Mp3File::getId).collect(Collectors.toSet());

        if(!existingIds.isEmpty()) {
            mp3FileRepository.deleteAllByIdInBatch(existingIds);
            messagePublisher.deleteMessage(existingIds);
        }

        return existingIds;
    }

    @Transactional
    Long save(String resourceId) {
        Mp3File mp3File = new Mp3File();
        mp3File.setResourceId(resourceId);
        mp3FileRepository.save(mp3File);
        return mp3File.getId();
    }
}
