package pl.wawrzyniak.NoteApp.Service.DTO;

import lombok.Data;

import java.util.List;

@Data
public class Page {
    private List<NoteDTO> notes;
    private PaginationInfo paginationInfo;
}
