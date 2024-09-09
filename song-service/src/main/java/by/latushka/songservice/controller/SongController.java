package by.latushka.songservice.controller;

import by.latushka.songservice.dto.SongDto;
import by.latushka.songservice.exception.SongNotFoundException;
import by.latushka.songservice.service.SongService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    @PostMapping
    public ResponseEntity<?> addSong(@RequestBody @Valid SongDto dto) {
        Long songId = songService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("id", songId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SongDto> getSong(@PathVariable Long id) throws SongNotFoundException {
        SongDto dto = songService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSong(@RequestParam(required = false) @Size(max = 199) Set<Long> id,
                                        @RequestParam(required = false) Boolean byResource) {
        Set<Long> removedIds;
        if(Boolean.TRUE.equals(byResource)) {
            removedIds = songService.deleteAllByResourceId(id);
        } else {
            removedIds = songService.deleteAll(id);
        }
        return ResponseEntity.ok(Map.of("id", removedIds));
    }
}
