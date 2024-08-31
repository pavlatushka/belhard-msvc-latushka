package by.latushka.resourceservice.service;

import by.latushka.resourceservice.dto.Mp3FileMetadata;
import by.latushka.resourceservice.entity.Mp3File;
import by.latushka.resourceservice.exception.InvalidResourceException;

import java.io.InputStream;
import java.util.Optional;
import java.util.Set;

public interface ResourceService {
    Optional<Mp3File> findById(Long id);

    void deleteAll(Set<Long> ids);

    Mp3FileMetadata parseMp3FileMetadata(InputStream is) throws InvalidResourceException;

    Mp3File uploadMp3File(InputStream is) throws InvalidResourceException;

    byte[] unwrapMp3FileData(Mp3File mp3File);
}
