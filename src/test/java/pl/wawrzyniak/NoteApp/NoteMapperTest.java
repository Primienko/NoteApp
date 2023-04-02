package pl.wawrzyniak.NoteApp;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wawrzyniak.NoteApp.Repository.Entities.Note;
import pl.wawrzyniak.NoteApp.Repository.Entities.User;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteDTO;
import pl.wawrzyniak.NoteApp.Service.Mappers.NoteMapper;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
class NoteMapperTest {
    @Autowired
    NoteMapper mapper;

    @Test
    void noteToNoteDTO() {
        //given
        User user = new User();
        user.setId(0L);
        Note note = new Note();
        note.setUser(user);

        //when
        NoteDTO noteDTO = mapper.noteToNoteDTO(note);

        //then
        assertEquals(0L, noteDTO.getUserId());
    }

    @Test
    void noteDTOToNote() {
        //given
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setUserId(0L);

        //when
        Note note = mapper.noteDTOToNote(noteDTO);

        //then
        assertEquals(0L, note.getUser().getId());
    }
}