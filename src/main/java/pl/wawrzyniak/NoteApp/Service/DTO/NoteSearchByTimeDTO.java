package pl.wawrzyniak.NoteApp.Service.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteSearchByTimeDTO {
    private LocalDateTime minUpdateTime;
    private LocalDateTime maxUpdateTime;
}
