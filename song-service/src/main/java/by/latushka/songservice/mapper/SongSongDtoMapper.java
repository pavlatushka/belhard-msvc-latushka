package by.latushka.songservice.mapper;

import by.latushka.songservice.dto.SongDto;
import by.latushka.songservice.entity.Song;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongSongDtoMapper {
    SongDto toDto(Song song);
    Song toEntity(SongDto songDto);
}
