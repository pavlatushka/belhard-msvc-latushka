package by.latushka.resourceservice.service;

import by.latushka.resourceservice.dto.Mp3FileMetadata;
import by.latushka.resourceservice.exception.InvalidResourceException;
import by.latushka.resourceservice.exception.ResourceNotFoundException;

import java.io.InputStream;
import java.util.Set;

public interface ResourceService {
    byte[] findById(Long id) throws ResourceNotFoundException;

    Mp3FileMetadata parseMp3FileMetadata(InputStream is) throws InvalidResourceException;

    Long uploadMp3File(InputStream is) throws InvalidResourceException;

    Set<Long> deleteAll(Set<Long> ids);
}
