package by.latushka.songservice.repository;

import by.latushka.songservice.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface SongRepository extends JpaRepository<Song, Long> {
    @Query("select s.id from Song s where s.id in (:ids)")
    Set<Long> findExistingIds(Set<Long> ids);
}
