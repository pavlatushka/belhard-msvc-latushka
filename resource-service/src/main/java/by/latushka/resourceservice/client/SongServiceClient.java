package by.latushka.resourceservice.client;

import by.latushka.resourceservice.dto.Mp3FileMetadata;
import by.latushka.resourceservice.dto.SongServiceClientException;

import java.util.Collection;
import java.util.List;

public interface SongServiceClient {
    void saveMetadata(Mp3FileMetadata metadata) throws SongServiceClientException;

    void deleteMetadata(Collection<Long> ids) throws SongServiceClientException;
}
