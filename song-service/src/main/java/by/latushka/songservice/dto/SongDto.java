package by.latushka.songservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SongDto {
    private String name;
    private String artist;
    private String album;
    private Double length;
    private Integer year;
    @NotNull
    private Long resourceId;
}
