package by.latushka.songservice.entity;

import by.latushka.songservice.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Song extends BaseEntity<Long> {
    private String name;
    private String artist;
    private String album;
    private Double length;
    private Integer year;
    @Column(nullable = false)
    private Long resourceId;
}
