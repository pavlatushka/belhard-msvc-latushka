package by.latushka.resourceprocessor.service.impl;

import by.latushka.resourceprocessor.dto.MetadataDto;
import by.latushka.resourceprocessor.service.MetadataService;
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

import java.io.IOException;
import java.io.InputStream;

@Log4j2
@Service
public class MetadataServiceImpl implements MetadataService {
    public MetadataDto parseMetadata(byte[] data) {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        Mp3Parser mp3Parser = new Mp3Parser();
        ParseContext context = new ParseContext();

        try (InputStream is = TikaInputStream.get(data)){
            mp3Parser.parse(is, handler, metadata, context);
        } catch (IOException | SAXException | TikaException e) {
            log.error("Failed to parse mp3 file", e);
            throw new RuntimeException(e);
        }

        MetadataDto dto = MetadataDto.builder()
                .name(metadata.get("dc:title"))
                .artist(metadata.get("xmpDM:artist"))
                .album(metadata.get("xmpDM:album"))
                .length(Double.valueOf(metadata.get("xmpDM:duration")))
                .year(metadata.getInt(Property.externalInteger("xmpDM:releaseDate")))
                .build();

        log.info("Parsed metadata: {}", dto);

        return dto;
    }
}
