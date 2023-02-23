package pl.wawrzyniak.NoteApp.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wawrzyniak.NoteApp.Repository.Entities.Note;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long>, NoteQueryRepository {
}
