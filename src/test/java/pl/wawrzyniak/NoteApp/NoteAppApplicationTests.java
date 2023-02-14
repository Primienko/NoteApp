package pl.wawrzyniak.NoteApp;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wawrzyniak.NoteApp.Controller.NoteRestController;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteCriteriaDTO;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

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

	private static Stream<Arguments> textTesting(){
		return Stream.of(
				Arguments.arguments("v1", 1),
				Arguments.arguments("*", 2),
				Arguments.arguments("v", 3),
				Arguments.arguments("test", 0)
		);
	}
	@ParameterizedTest
	@MethodSource("textTesting")
	void findByTextTest(String text, int value){
		// given
		NoteDTO note1 = new NoteDTO();
		String noteContent1 = "Test v1 *";
		note1.setText(noteContent1);
		NoteDTO note2 = new NoteDTO();
		String noteContent2 = "Test v2";
		note2.setText(noteContent2);
		NoteDTO note3 = new NoteDTO();
		String noteContent3 = "Test v3 *";
		note3.setText(noteContent3);
		noteRestController.addNote(note1);
		noteRestController.addNote(note2);
		noteRestController.addNote(note3);

		// when
		NoteCriteriaDTO criteriaDTO = new NoteCriteriaDTO();
		criteriaDTO.setText(text);
		List<NoteDTO> result = noteRestController.getByCriteria(criteriaDTO);

		// then
		assertEquals(value, result.size());
	}

	private static void sleep(int ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	@Test
	void findByMinDateTest(){
		// given
		NoteDTO note1 = new NoteDTO();
		String noteContent1 = "Test v1 *";
		note1.setText(noteContent1);
		NoteDTO note2 = new NoteDTO();
		String noteContent2 = "Test v2";
		note2.setText(noteContent2);
		NoteDTO note3 = new NoteDTO();
		String noteContent3 = "Test v3 *";
		note3.setText(noteContent3);
		note1 = noteRestController.addNote(note1);
		sleep(100);
		note2 = noteRestController.addNote(note2);
		sleep(100);
		note3 = noteRestController.addNote(note3);

		// when
		NoteCriteriaDTO criteriaDTO = new NoteCriteriaDTO();
		criteriaDTO.setMinUpdateTime(note2.getUpdateTime());
		List<NoteDTO> result = noteRestController.getByCriteria(criteriaDTO);

		// then
		assertEquals(2, result.size());
	}

	@Test
	void findByMaxDateTest(){
		// given
		NoteDTO note1 = new NoteDTO();
		String noteContent1 = "Test v1 *";
		note1.setText(noteContent1);
		NoteDTO note2 = new NoteDTO();
		String noteContent2 = "Test v2";
		note2.setText(noteContent2);
		NoteDTO note3 = new NoteDTO();
		String noteContent3 = "Test v3 *";
		note3.setText(noteContent3);
		note1 = noteRestController.addNote(note1);
		sleep(100);
		note2 = noteRestController.addNote(note2);
		sleep(100);
		note3 = noteRestController.addNote(note3);

		// when
		NoteCriteriaDTO criteriaDTO = new NoteCriteriaDTO();
		criteriaDTO.setMaxUpdateTime(note2.getUpdateTime());
		List<NoteDTO> result = noteRestController.getByCriteria(criteriaDTO);

		// then
		assertEquals(2, result.size());
	}



}
