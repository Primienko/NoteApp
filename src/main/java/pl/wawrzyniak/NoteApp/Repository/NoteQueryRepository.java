package pl.wawrzyniak.NoteApp.Repository;

import pl.wawrzyniak.NoteApp.Criteria.NoteCriteria;
import pl.wawrzyniak.NoteApp.Repository.Entities.Note;

import java.util.List;

public interface NoteQueryRepository {
    List<Note> findByCriteria(NoteCriteria criteria);
}
