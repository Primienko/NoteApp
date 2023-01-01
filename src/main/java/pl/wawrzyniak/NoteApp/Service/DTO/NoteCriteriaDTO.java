package pl.wawrzyniak.NoteApp.Service.DTO;

import java.time.LocalDateTime;

public class NoteCriteriaDTO {
    private Long id;
    private String text;
    private LocalDateTime minUpdateTime;
    private LocalDateTime maxUpdateTime;
}
