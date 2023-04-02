package pl.wawrzyniak.NoteApp.Service.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteCriteriaDTO {
    private String text;
    private LocalDateTime minUpdateTime;
    private LocalDateTime maxUpdateTime;
    private Long userId;
}
