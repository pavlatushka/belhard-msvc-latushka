package by.latushka.songservice.service.impl;

import by.latushka.songservice.dto.SongDto;
import by.latushka.songservice.entity.Song;
import by.latushka.songservice.exception.SongNotFoundException;
import by.latushka.songservice.mapper.SongSongDtoMapper;
import by.latushka.songservice.repository.SongRepository;
import by.latushka.songservice.service.SongService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class SongServiceImpl implements SongService {
    private final SongSongDtoMapper mapper;
    private final SongRepository songRepository;

    @Override
    public SongDto findById(Long id) throws SongNotFoundException {
        Optional<Song> song = songRepository.findById(id);
        if(song.isEmpty()) {
            throw new SongNotFoundException();
        }
        return mapper.toDto(song.get());
    }

    @Transactional
    @Override
    public Long save(SongDto dto) {
        Song song = mapper.toEntity(dto);
        songRepository.save(song);
        return song.getId();
    }

    @Override
    @Transactional
    public void deleteAll(Set<Long> ids) {
        if(ids == null || ids.isEmpty()) {
            return;
        }
        songRepository.deleteAllByIdInBatch(ids);
    }
}
