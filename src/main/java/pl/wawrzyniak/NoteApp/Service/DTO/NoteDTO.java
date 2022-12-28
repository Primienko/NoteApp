package pl.wawrzyniak.NoteApp.Service.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteDTO {
    private Long id;
    private String text;
    private LocalDateTime updateTime;
}
