package pl.wawrzyniak.NoteApp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wawrzyniak.NoteApp.Repository.Entities.Note;
import pl.wawrzyniak.NoteApp.Repository.NoteRepository;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteDTO;
import pl.wawrzyniak.NoteApp.Service.Mappers.NoteMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    private NoteRepository noteRepository;
    private NoteMapper noteMapper;

    @Autowired
    public NoteService(NoteRepository noteRepository, NoteMapper noteMapper){
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    public NoteDTO save(NoteDTO note){
        Note noteToSave = this.noteMapper.noteDTOToNote(note);
        Note savedNote =  noteRepository.save(noteToSave);
        return this.noteMapper.noteToNoteDTO(savedNote);
    }

    public List<NoteDTO> getAll() {
        List<Note> list = new ArrayList<>();
        noteRepository.findAll().forEach(list::add);
        return this.noteMapper.noteListToDTOList(list);
    }
}
