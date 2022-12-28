package pl.wawrzyniak.NoteApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wawrzyniak.NoteApp.Controller.NoteRestController;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteDTO;

import java.util.List;

@SpringBootTest
class NoteAppApplicationTests {
	@Autowired
	NoteRestController noteRestController;

	@BeforeEach
	void prepareCleanDB() {
		noteRestController.delateAll();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void saveOneNoteTest() {
		// given
		NoteDTO note = new NoteDTO();
		String noteContent = "Test";
		note.setText(noteContent);

		// when
		NoteDTO result = noteRestController.addNote(note);

		// then
		assertNotNull(result.getId());
		assertNotNull(result.getUpdateTime());
		assertEquals(noteContent, result.getText());
	}

	@Test
	void getAllNotesTest() {
		// given
		int requestNumber = 10;
		for(int i = 0; i < requestNumber; i++) {
			NoteDTO note = new NoteDTO();
			String noteContent = "Test" + i;
			note.setText(noteContent);
			noteRestController.addNote(note);
		}

		// when
		List<NoteDTO> result = noteRestController.getAllNotes();

		// then
		assertEquals(requestNumber, result.size());
	}

	@Test
	void delateAllTest(){
		// given
		int requestNumber = 10;
		for(int i = 0; i < requestNumber; i++) {
			NoteDTO note = new NoteDTO();
			String noteContent = "Test" + i;
			note.setText(noteContent);
			noteRestController.addNote(note);
		}
		List<NoteDTO> result = noteRestController.getAllNotes();
		assertEquals(requestNumber, result.size());

		// when
		noteRestController.delateAll();

		// then
		result = noteRestController.getAllNotes();
		assertEquals(0, result.size());

	}
}
