package pl.wawrzyniak.NoteApp.Criteria;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteCriteria {
    private String text;
    private LocalDateTime minUpdateTime;
    private LocalDateTime maxUpdateTime;
    private Long userId;
}


