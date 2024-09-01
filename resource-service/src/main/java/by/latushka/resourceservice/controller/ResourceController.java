package by.latushka.resourceservice.controller;

import by.latushka.resourceservice.dto.Mp3FileMetadata;
import by.latushka.resourceservice.exception.InvalidResourceException;
import by.latushka.resourceservice.exception.ResourceNotFoundException;
import by.latushka.resourceservice.service.ResourceService;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/resources")
public class ResourceController {
    private final ResourceService resourceService;

    @PostMapping(consumes = "audio/mpeg")
    public ResponseEntity<?> uploadResource(InputStream is) throws InvalidResourceException {
        Mp3FileMetadata metadata = resourceService.parseMp3FileMetadata(is);
        Long mp3FileId = resourceService.uploadMp3File(is);

        //todo send metadata to Song Service

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("id", mp3FileId));
    }

    @GetMapping(value = "/{id}", produces = "audio/mpeg")
    public ResponseEntity<byte[]> getResource(@PathVariable Long id) throws ResourceNotFoundException {
        byte[] resourceData = resourceService.findById(id);
        return ResponseEntity.ok(resourceData);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteResource(@RequestParam(required = false) @Size(max = 199) Set<Long> id) {
        Set<Long> removedIds = resourceService.deleteAll(id);
        return ResponseEntity.ok(Map.of("id", removedIds));
    }
}
