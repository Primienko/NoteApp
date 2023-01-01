package pl.wawrzyniak.NoteApp.Repository;

import org.springframework.stereotype.Repository;
import pl.wawrzyniak.NoteApp.Criteria.NoteCriteria;
import pl.wawrzyniak.NoteApp.Repository.Entities.Note;

import java.util.List;

@Repository
public class NoteQueryRespository {
    List<Note> findByCriteria(NoteCriteria criteria){
        //todo: implement query search
    }
}
