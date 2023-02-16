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

import java.util.stream.Stream;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
class NoteAppApplicationTests {
	@Autowired
	NoteRestController noteRestController;


	private static String noteDtoToJson(NoteDTO noteDTO) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("text", noteDTO.getText());
		return json.toString();
	}

	private static String criteriaToJson(NoteCriteriaDTO criteriaDTO) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("text", criteriaDTO.getText());
		json.put("minUpdateTime", criteriaDTO.getMinUpdateTime());
		json.put("maxUpdateTime", criteriaDTO.getMaxUpdateTime());
		return json.toString();
	}

	private static NoteDTO[] getNotesByCriteria(NoteCriteriaDTO criteriaDTO) throws JSONException {
		NoteDTO[] result = given()
				.when()
				.body(criteriaToJson(criteriaDTO))
				.contentType(ContentType.JSON)
				.post("/api/note/find")
				.then()
				.statusCode(200)
				.extract().as(NoteDTO[].class);
		return result;
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
		noteRestController.delateAll();
	}

	@Test
	void contextLoads() {
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
	void getAllNotesTest() throws JSONException {
		// given
		int requestNumber = 10;
		for(int i = 0; i < requestNumber; i++) {
			NoteDTO note = new NoteDTO();
			String noteContent = "Test" + i;
			note.setText(noteContent);
			saveOneNote(note);
		}

		// when
		NoteDTO[] result = given().when().get("/api/note/all").then().statusCode(200).extract().as(NoteDTO[].class);

		// then
		assertEquals(requestNumber, result.length);
	}

	@Test
	void delateAllTest() throws JSONException {
		// given
		int requestNumber = 10;
		for(int i = 0; i < requestNumber; i++) {
			NoteDTO note = new NoteDTO();
			String noteContent = "Test" + i;
			note.setText(noteContent);
			saveOneNote(note);
		}
		NoteDTO[] result = given()
				.when()
				.get("/api/note/all")
				.then()
				.statusCode(200)
				.extract().as(NoteDTO[].class);
		assertEquals(requestNumber, result.length);

		// when
		given()
				.when()
				.delete("/api/note/all")
				.then()
				.statusCode(200);

		// then
		NoteDTO[] finalResult = given()
				.when()
				.get("/api/note/all")
				.then()
				.statusCode(200)
				.extract()
				.as(NoteDTO[].class);
		assertEquals(0, finalResult.length);
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
		NoteDTO[] result = getNotesByCriteria(criteriaDTO);

		// then
		assertEquals(value, result.length);
	}

	private static void sleep(int ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	@Test
	void findByMinDateTest() throws JSONException {
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
		sleep(100);
		note2 = saveOneNote(note2);
		sleep(100);
		note3 = saveOneNote(note3);

		// when
		NoteCriteriaDTO criteriaDTO = new NoteCriteriaDTO();
		criteriaDTO.setMaxUpdateTime(note2.getUpdateTime());
		NoteDTO[] result = getNotesByCriteria(criteriaDTO);


		// then
		assertEquals(2, result.length);
	}

	@Test
	void findByMaxDateTest() throws JSONException {
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
		sleep(100);
		note2 = saveOneNote(note2);
		sleep(100);
		note3 = saveOneNote(note3);

		// when
		NoteCriteriaDTO criteriaDTO = new NoteCriteriaDTO();
		criteriaDTO.setMaxUpdateTime(note2.getUpdateTime());
		NoteDTO[] result = getNotesByCriteria(criteriaDTO);

		// then
		assertEquals(2, result.length);
	}

	@Test
	void emptyPredicateTest() throws JSONException {
		// given

		// when
		NoteCriteriaDTO criteriaDTO = new NoteCriteriaDTO();;
		int status = given()
				.when()
				.body(criteriaToJson(criteriaDTO))
				.contentType(ContentType.JSON)
				.post("/api/note/find")
				.statusCode();

		// then
		assertEquals(400, status);

	}
}
