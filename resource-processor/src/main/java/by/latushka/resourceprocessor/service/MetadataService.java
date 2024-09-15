package by.latushka.resourceprocessor.service;

import by.latushka.resourceprocessor.dto.MetadataDto;

public interface MetadataService {
    MetadataDto parseMetadata(byte[] data);
}
