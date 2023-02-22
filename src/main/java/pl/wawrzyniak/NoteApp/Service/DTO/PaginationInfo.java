package pl.wawrzyniak.NoteApp.Service.DTO;

import lombok.Data;

@Data
public class PaginationInfo {
    private Integer allPages;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer offset;
}