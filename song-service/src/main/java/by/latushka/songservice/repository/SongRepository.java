package by.latushka.songservice.repository;

import by.latushka.songservice.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
