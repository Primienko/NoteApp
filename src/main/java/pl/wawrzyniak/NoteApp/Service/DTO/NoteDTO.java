package pl.wawrzyniak.NoteApp.Service.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteDTO {
    private Long id;
    private Integer version;
    private String text;
    private LocalDateTime updateTime;
    private Long userId;
}
