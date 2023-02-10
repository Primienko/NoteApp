package pl.wawrzyniak.NoteApp.Service.Mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.wawrzyniak.NoteApp.Criteria.NoteCriteria;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteCriteriaDTO;

@Service
public class CriteriaMapper {
    private final static ModelMapper mapper = new ModelMapper();

    public NoteCriteria dtoToCriteria(NoteCriteriaDTO criteriaDTO) {return mapper.map(criteriaDTO, NoteCriteria.class);}

    public NoteCriteriaDTO criteriaToDto(NoteCriteria criteria) {return mapper.map(criteria, NoteCriteriaDTO.class);}
}
