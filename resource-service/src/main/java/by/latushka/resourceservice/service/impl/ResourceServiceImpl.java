package by.latushka.resourceservice.service.impl;

import by.latushka.resourceservice.client.SongServiceClient;
import by.latushka.resourceservice.client.StorageClient;
import by.latushka.resourceservice.dto.Mp3FileMetadata;
import by.latushka.resourceservice.dto.SongServiceClientException;
import by.latushka.resourceservice.entity.Mp3File;
import by.latushka.resourceservice.exception.InvalidResourceException;
import by.latushka.resourceservice.exception.ResourceNotFoundException;
import by.latushka.resourceservice.repository.Mp3FileRepository;
import by.latushka.resourceservice.service.ResourceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Property;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class ResourceServiceImpl implements ResourceService {
    private final Mp3FileRepository mp3FileRepository;
    private final SongServiceClient songServiceClient;
    private final StorageClient storageClient;

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
        Mp3FileMetadata metadata = parseMp3FileMetadata(rawData);

        String resourceId = UUID.randomUUID().toString();

        storageClient.put(resourceId, rawData);
        Long mp3FileId = save(resourceId);

        if (mp3FileId != null) {
            metadata.setResourceId(mp3FileId);
            try {
                songServiceClient.saveMetadata(metadata);
            } catch (SongServiceClientException e) { //rollback resource service changes
                log.error("Auto delete saved Mp3File with id {} due SongService error", mp3FileId, e);
                mp3FileRepository.deleteById(mp3FileId);
                mp3FileId = null;
            }
        }
        return mp3FileId;
    }

    @Override
    @Transactional
    public Set<Long> deleteAll(Set<Long> ids) {
        if(ids == null || ids.isEmpty()) {
            return Set.of();
        }
        Set<Mp3File> files = mp3FileRepository.findExisting(ids);

        files.forEach(f -> storageClient.delete(f.getResourceId()));

        mp3FileRepository.deleteAllByIdInBatch(ids);
        return files.stream().map(Mp3File::getId).collect(Collectors.toSet());
    }

    @Transactional
    Long save(String resourceId) {
        Mp3File mp3File = new Mp3File();
        mp3File.setResourceId(resourceId);
        mp3FileRepository.save(mp3File);
        return mp3File.getId();
    }

    private Mp3FileMetadata parseMp3FileMetadata(byte[] data) throws InvalidResourceException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        Mp3Parser mp3Parser = new Mp3Parser();
        ParseContext context = new ParseContext();

        try (InputStream is = TikaInputStream.get(data)){
            mp3Parser.parse(is, handler, metadata, context);
        } catch (IOException | SAXException | TikaException e) {
            log.error("Failed to parse mp3 file", e);
            throw new InvalidResourceException(e);
        }

        Mp3FileMetadata dto = Mp3FileMetadata.builder()
                .name(metadata.get("dc:title"))
                .artist(metadata.get("xmpDM:artist"))
                .album(metadata.get("xmpDM:album"))
                .length(Double.valueOf(metadata.get("xmpDM:duration")))
                .year(metadata.getInt(Property.externalInteger("xmpDM:releaseDate")))
                .build();

        return dto;
    }
}
