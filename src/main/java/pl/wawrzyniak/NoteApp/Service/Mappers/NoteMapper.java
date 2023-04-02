package pl.wawrzyniak.NoteApp.Service.Mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;
import pl.wawrzyniak.NoteApp.Repository.Entities.Note;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteMapper {
    private static final ModelMapper mapper = new ModelMapper();

    NoteMapper(){
        TypeMap<Note, NoteDTO> typeMapToDTO = this.mapper.createTypeMap(Note.class, NoteDTO.class);
        typeMapToDTO.addMapping(src -> src.getUser().getId(), NoteDTO::setUserId);
        TypeMap<NoteDTO, Note> typeMapToNote = mapper.createTypeMap(NoteDTO.class, Note.class);
        typeMapToNote.addMapping(NoteDTO::getUserId, (dest, v) -> dest.getUser().setId((Long) v));
    }

    public NoteDTO noteToNoteDTO(Note note){
        return mapper.map(note, NoteDTO.class);
    }

    public Note noteDTOToNote(NoteDTO noteDTO){
        return mapper.map(noteDTO, Note.class);
    }

    public List<NoteDTO> noteListToDTOList(List<Note> list){
        return list.stream().map(this::noteToNoteDTO).collect(Collectors.toList());
    }

    public List<Note> noteDTOListToNoteList(List<NoteDTO> list){
        return list.stream().map(this::noteDTOToNote).collect(Collectors.toList());
    }
}
