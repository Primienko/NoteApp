package pl.wawrzyniak.NoteApp;

import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.wawrzyniak.NoteApp.Controller.NoteRestController;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteCriteriaDTO;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteDTO;
import pl.wawrzyniak.NoteApp.Service.DTO.Page;
import pl.wawrzyniak.NoteApp.Service.DTO.PaginationInfo;


import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
class NoteAppApplicationTests {
	@Autowired
	NoteRestController noteRestController;

	private static void sleep(int s) throws InterruptedException {
		TimeUnit.SECONDS.sleep(s);
	}

	private static String noteDtoToJson(NoteDTO noteDTO) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("text", noteDTO.getText());
		json.put("id", noteDTO.getId());
		json.put("version", noteDTO.getVersion());
		return json.toString();
	}

	private static JSONObject criteriaToJson(NoteCriteriaDTO criteriaDTO) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("text", criteriaDTO.getText());
		json.put("minUpdateTime", criteriaDTO.getMinUpdateTime());
		json.put("maxUpdateTime", criteriaDTO.getMaxUpdateTime());
		return json;
	}

	private static String paginationInfoToJson(PaginationInfo info) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("pageSize", info.getPageSize());
		json.put("pageNumber", info.getPageNumber());
		json.put("offset", info.getOffset());
		json.put("allPages", info.getAllPages());
		json.put("criteria", criteriaToJson(info.getCriteria()));
		return json.toString();
	}
	private static Page getNotesByCriteria(PaginationInfo info) throws JSONException {
		return given()
				.when()
				.body(paginationInfoToJson(info))
				.contentType(ContentType.JSON)
				.post("/api/note/find")
				.then()
				.statusCode(200)
				.extract().as(Page.class);
	}

	private static NoteDTO saveOneNote(NoteDTO noteDTO) throws JSONException {
		String json = noteDtoToJson(noteDTO);
		return given()
				.body(json).contentType(ContentType.JSON)
				.post("/api/note")
				.then()
				.statusCode(200)
				.extract().as(NoteDTO.class);
	}
	@BeforeEach
	void prepareCleanDB() {
		noteRestController.deleteAll();
	}


	@Test
	void saveOneNoteTest() throws JSONException {
		// given
		NoteDTO note = new NoteDTO();
		String noteContent = "Test";
		note.setText(noteContent);

		// when
		NoteDTO result = saveOneNote(note);

		// then
		assertNotNull(result.getId());
		assertNotNull(result.getUpdateTime());
		assertEquals(noteContent, result.getText());
	}

	@Test
	void saveEmptyNoteTest() throws JSONException {
		//given
		NoteDTO note = new NoteDTO();
		String noteContent = "";
		note.setText(noteContent);
		String json = noteDtoToJson(note);

		// when
		int response = given()
				.body(json).contentType(ContentType.JSON)
				.post("/api/note").statusCode();

		//then
		assertEquals(400, response);

	}

	@Test
	void optimisticLockingTest() throws JSONException {
		NoteDTO note = new NoteDTO();
		String noteContent = "Test Message";
		note.setText(noteContent);

		NoteDTO result = saveOneNote(note);

		NoteDTO editedNote = new NoteDTO();
		String editedContent = "New Message";
		editedNote.setText(editedContent);
		editedNote.setVersion(2137);
		editedNote.setId(result.getId());
		// when
		String editedJson = noteDtoToJson(editedNote);
		int statusCode = given()
				.body(editedJson).contentType(ContentType.JSON)
				.post("/api/note/update").statusCode();

		// then
		assertEquals(409, statusCode);
	}
	@Test
	void editNoteTest() throws JSONException {
		// given
		NoteDTO note = new NoteDTO();
		String noteContent = "Test Message";
		note.setText(noteContent);

		NoteDTO result = saveOneNote(note);

		NoteDTO editedNote = new NoteDTO();
		String editedContent = "New Message";
		editedNote.setText(editedContent);
		editedNote.setId(result.getId());
		editedNote.setVersion(result.getVersion());
		// when
		String editedJson = noteDtoToJson(editedNote);
		NoteDTO editedResult = given()
				.body(editedJson).contentType(ContentType.JSON)
				.post("/api/note/update")
				.then()
				.statusCode(200)
				.extract().as(NoteDTO.class);

		// then
		assertEquals(editedContent, editedResult.getText());
	}

	@Test
	void deleteNoteTest() throws JSONException {
		// given
		NoteDTO note = new NoteDTO();
		String noteContent = "Test Message";
		note.setText(noteContent);
		NoteDTO savedNote = saveOneNote(note);

		// when
		given()
				.when()
				.delete("/api/note?id=" + savedNote.getId())
				.then()
				.statusCode(200);
	}
	@Test
	void getPaginationTest() throws JSONException {
		// given
		int requestNumber = 10;
		for(int i = 0; i < requestNumber; i++) {
			NoteDTO note = new NoteDTO();
			String noteContent = "Test" + i;
			note.setText(noteContent);
			saveOneNote(note);
		}

		// when
		NoteCriteriaDTO criteriaDTO = new NoteCriteriaDTO();
		PaginationInfo info = new PaginationInfo();
		info.setOffset(0);
		info.setPageNumber(0);
		info.setPageSize(5);
		info.setCriteria(criteriaDTO);
		Page result = getNotesByCriteria(info);
		NoteDTO[] noteDTOS = result.getNotes().toArray(new NoteDTO[0]);
		// then
		assertEquals(5, noteDTOS.length);
		assertEquals(2, result.getPaginationInfo().getAllPages());
	}

	@Test
	void deleteAllTest() throws JSONException {
		// given
		int requestNumber = 10;
		for(int i = 0; i < requestNumber; i++) {
			NoteDTO note = new NoteDTO();
			String noteContent = "Test" + i;
			note.setText(noteContent);
			saveOneNote(note);
		}
		// when
		given()
				.when()
				.delete("/api/note/all")
				.then()
				.statusCode(200);
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
	void findByTextTest(String text, int value) throws JSONException {
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
		saveOneNote(note1);
		saveOneNote(note2);
		saveOneNote(note3);

		// when
		NoteCriteriaDTO criteriaDTO = new NoteCriteriaDTO();
		criteriaDTO.setText(text);
		PaginationInfo info = new PaginationInfo();
		info.setOffset(0);
		info.setPageNumber(0);
		info.setPageSize(100);
		info.setCriteria(criteriaDTO);
		Page result = getNotesByCriteria(info);
		NoteDTO[] noteDTOS = result.getNotes().toArray(new NoteDTO[0]);
		// then
		assertEquals(value, noteDTOS.length);
	}

	@Test
	void findByMinDateTest() throws JSONException, InterruptedException {
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
		note1 = saveOneNote(note1);
		sleep(1);
		note2 = saveOneNote(note2);
		sleep(1);
		note3 = saveOneNote(note3);

		// when
		NoteCriteriaDTO criteriaDTO = new NoteCriteriaDTO();
		criteriaDTO.setMaxUpdateTime(note2.getUpdateTime());
		PaginationInfo info = new PaginationInfo();
		info.setOffset(0);
		info.setPageNumber(0);
		info.setPageSize(100);
		info.setCriteria(criteriaDTO);
		Page result = getNotesByCriteria(info);
		NoteDTO[] noteDTOS = result.getNotes().toArray(new NoteDTO[0]);
		// then

		// then
		assertEquals(2, noteDTOS.length);
	}

	@Test
	void findByMaxDateTest() throws JSONException, InterruptedException {
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
		note1 = saveOneNote(note1);
		sleep(1);
		note2 = saveOneNote(note2);
		sleep(1);
		note3 = saveOneNote(note3);

		// when
		NoteCriteriaDTO criteriaDTO = new NoteCriteriaDTO();
		criteriaDTO.setMaxUpdateTime(note2.getUpdateTime());
		PaginationInfo info = new PaginationInfo();
		info.setOffset(0);
		info.setPageNumber(0);
		info.setPageSize(100);
		info.setCriteria(criteriaDTO);
		Page result = getNotesByCriteria(info);
		NoteDTO[] noteDTOS = result.getNotes().toArray(new NoteDTO[0]);
		// then

		// then
		assertEquals(2, noteDTOS.length);
	}
}
