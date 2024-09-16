package by.latushka.resourceprocessor.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MetadataDto {
    private Long resourceId;
    private String name;
    private String artist;
    private String album;
    private Double length;
    private Integer year;
}