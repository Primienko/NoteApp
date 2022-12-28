package pl.wawrzyniak.NoteApp.Service.Mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.wawrzyniak.NoteApp.Repository.Entities.Note;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteDTO;

@Service
public class NoteMapper {
    private static final ModelMapper mapper = new ModelMapper();

    public NoteDTO noteToNoteDTO(Note note){
        return mapper.map(note, NoteDTO.class);
    }

    public Note noteDTOToNote(NoteDTO noteDTO){
        return mapper.map(noteDTO, Note.class);
    }
}
