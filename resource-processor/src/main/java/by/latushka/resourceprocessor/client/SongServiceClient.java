package by.latushka.resourceprocessor.client;

import by.latushka.resourceprocessor.dto.MetadataDto;

public interface SongServiceClient {
    void postMetadata(MetadataDto dto);
}
