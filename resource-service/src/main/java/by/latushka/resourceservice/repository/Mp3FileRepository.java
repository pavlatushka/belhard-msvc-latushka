package by.latushka.resourceservice.repository;

import by.latushka.resourceservice.entity.Mp3File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Mp3FileRepository extends JpaRepository<Mp3File, Long> {
}
