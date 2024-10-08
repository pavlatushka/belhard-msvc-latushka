package by.latushka.songservice.service;

import by.latushka.songservice.dto.SongDto;
import by.latushka.songservice.exception.SongNotFoundException;

import java.util.Set;

public interface SongService {
    SongDto findById(Long id) throws SongNotFoundException;

    Long save(SongDto dto);

    Set<Long> deleteAll(Set<Long> ids);

    Set<Long> deleteAllByResourceId(Set<Long> ids);
}
