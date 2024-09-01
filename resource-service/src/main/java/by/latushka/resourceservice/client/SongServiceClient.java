package by.latushka.resourceservice.client;

import by.latushka.resourceservice.dto.Mp3FileMetadata;
import by.latushka.resourceservice.dto.SongServiceClientException;

public interface SongServiceClient {
    void saveMetadata(Mp3FileMetadata metadata) throws SongServiceClientException;
}
