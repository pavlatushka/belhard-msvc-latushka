package by.latushka.songservice.service.impl;

import by.latushka.songservice.dto.SongDto;
import by.latushka.songservice.entity.Song;
import by.latushka.songservice.exception.SongNotFoundException;
import by.latushka.songservice.mapper.SongSongDtoMapper;
import by.latushka.songservice.repository.SongRepository;
import by.latushka.songservice.service.SongService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Log4j2
@RequiredArgsConstructor
@Service
public class SongServiceImpl implements SongService {
    private final SongSongDtoMapper mapper;
    private final SongRepository songRepository;

    @Override
    public SongDto findById(Long id) throws SongNotFoundException {
        Optional<Song> song = songRepository.findById(id);
        if(song.isEmpty()) {
            log.info("Song with id {} not found", id);
            throw new SongNotFoundException();
        }
        return mapper.toDto(song.get());
    }

    @Override
    @Transactional
    public Long save(SongDto dto) {
        Song song = mapper.toEntity(dto);
        songRepository.save(song);
        return song.getId();
    }

    @Override
    @Transactional
    public Set<Long> deleteAll(Set<Long> ids) {
        if(ids == null || ids.isEmpty()) {
            return Set.of();
        }
        Set<Long> existingIds = songRepository.findExistingIds(ids);
        songRepository.deleteAllByIdInBatch(ids);
        return existingIds;
    }
}
