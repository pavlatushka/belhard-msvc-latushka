package by.latushka.resourceservice.repository;

import by.latushka.resourceservice.entity.Mp3File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface Mp3FileRepository extends JpaRepository<Mp3File, Long> {
    @Query("select m.id from Mp3File m where m.id in (:ids)")
    Set<Long> findExistingIds(Set<Long> ids);
}
