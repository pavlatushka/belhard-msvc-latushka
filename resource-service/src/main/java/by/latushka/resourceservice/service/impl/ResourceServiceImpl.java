package by.latushka.resourceservice.service.impl;

import by.latushka.resourceservice.dto.Mp3FileMetadata;
import by.latushka.resourceservice.entity.Mp3File;
import by.latushka.resourceservice.exception.InvalidResourceException;
import by.latushka.resourceservice.exception.ResourceNotFoundException;
import by.latushka.resourceservice.repository.Mp3FileRepository;
import by.latushka.resourceservice.service.ResourceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tika.exception.TikaException;
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

@Log4j2
@RequiredArgsConstructor
@Service
public class ResourceServiceImpl implements ResourceService {
    private final Mp3FileRepository mp3FileRepository;

    @Override
    public byte[] findById(Long id) throws ResourceNotFoundException {
        Optional<Mp3File> mp3File = mp3FileRepository.findById(id);
        if(mp3File.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return unwrapMp3FileData(mp3File.get());
    }

    @Override
    public Mp3FileMetadata parseMp3FileMetadata(InputStream is) throws InvalidResourceException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        Mp3Parser mp3Parser = new Mp3Parser();
        ParseContext context = new ParseContext();

        try {
            mp3Parser.parse(is, handler, metadata, context);
        } catch (IOException | SAXException | TikaException e) {
            log.error("Failed to parse mp3 file", e);
            throw new InvalidResourceException(e);
        }

        Mp3FileMetadata dto = Mp3FileMetadata.builder()
                .title(metadata.get("dc:title"))
                .artist(metadata.get("xmpDM:artist"))
                .album(metadata.get("xmpDM:album"))
                .length(Double.valueOf(metadata.get("xmpDM:duration")))
                .year(metadata.getInt(Property.externalInteger("xmpDM:releaseDate")))
                .build();

        return dto;
    }

    @Override
    @Transactional
    public Long uploadMp3File(InputStream is) throws InvalidResourceException {
        SerialBlob blob;
        try {
            blob = new SerialBlob(is.readAllBytes());
        } catch (IOException | SQLException e) {
            log.error("Failed to read mp3 file data", e);
            throw new InvalidResourceException(e);
        }

        Mp3File mp3File = new Mp3File();
        mp3File.setData(blob);
        mp3FileRepository.save(mp3File);

        return mp3File.getId();
    }

    @Override
    @Transactional
    public void deleteAll(Set<Long> ids) {
        if(ids == null || ids.isEmpty()) {
            return;
        }
        mp3FileRepository.deleteAllByIdInBatch(ids);
    }

    @Transactional
    byte[] unwrapMp3FileData(Mp3File mp3File) {
        try {
            Blob blob = mp3File.getData();
            int blobLength = (int) blob.length();
            return blob.getBytes(1, blobLength);
        } catch (SQLException e) {
            log.error("Failed to convert mp3 file blob to byte array", e);
            return null;
        }
    }
}
