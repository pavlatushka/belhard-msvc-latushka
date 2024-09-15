package by.latushka.resourceservice.entity;

import by.latushka.resourceservice.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Blob;

@Data
@Entity
@Table(name = "mp3_file")
public class Mp3File extends BaseEntity<Long> {
    private String resourceId;
}
