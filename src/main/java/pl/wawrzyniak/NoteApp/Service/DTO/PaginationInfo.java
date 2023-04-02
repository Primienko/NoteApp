package pl.wawrzyniak.NoteApp.Service.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PaginationInfo {
    @JsonIgnore
    final private int maxPageSize = 50;
    @JsonIgnore
    final private int minPageSize = 1;
    private Long allPages;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer offset;
    private NoteCriteriaDTO criteria;

    public void setDefault(){
        if(this.pageNumber == null){
            this.pageNumber = 0;
        }
        if(this.pageSize == null){
            this.pageSize = 5;
        }
        if(this.offset == null){
            this.offset = 0;
        }
        if(this.criteria == null){
            this.criteria = new NoteCriteriaDTO();
        }
        if(this.pageSize > this.maxPageSize){
            this.pageSize = this.maxPageSize;
        }
        if(this.pageSize < this.minPageSize){
            this.pageSize = this.minPageSize;
        }
        if(this.pageNumber < 0){
            this.pageNumber = 0;
        }
        if(this.pageSize * this.pageNumber + this.offset < 0){
            this.offset = 0;
        }
    }
}