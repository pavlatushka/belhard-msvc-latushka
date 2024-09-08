package by.latushka.resourceservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Mp3FileMetadata {
    private Long resourceId;
    private String name;
    private String artist;
    private String album;
    private Double length;
    private Integer year;
}
