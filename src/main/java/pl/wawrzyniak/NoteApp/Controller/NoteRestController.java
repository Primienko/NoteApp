package pl.wawrzyniak.NoteApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wawrzyniak.NoteApp.Repository.CustomExeption.EmptyPredicateException;
import pl.wawrzyniak.NoteApp.Repository.CustomExeption.NoteNotExistsException;
import pl.wawrzyniak.NoteApp.Repository.CustomExeption.VerificationException;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteCriteriaDTO;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteDTO;
import pl.wawrzyniak.NoteApp.Service.DTO.Page;
import pl.wawrzyniak.NoteApp.Service.DTO.PaginationInfo;
import pl.wawrzyniak.NoteApp.Service.NoteService;

import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteRestController {
    private NoteService noteService;

    @Autowired
    public NoteRestController(NoteService noteService){
        this.noteService = noteService;
    }
    @PostMapping("")
    public NoteDTO addNote(@RequestBody NoteDTO note) throws VerificationException {
        return this.noteService.save(note);
    }
    @GetMapping("/all")
    public List<NoteDTO> getAllNotes(){
        return this.noteService.getAll();
    }

    @PostMapping("/find")
    public List<NoteDTO> getByCriteria(@RequestBody NoteCriteriaDTO criteriaDTO) throws EmptyPredicateException {
        return this.noteService.getByCriteria(criteriaDTO);
    }

    @DeleteMapping("/all")
    public ResponseEntity deleteAll(){
        this.noteService.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity deleteOne(@RequestParam Long id){
        this.noteService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/update")
    public NoteDTO updateNote(@RequestBody NoteDTO noteDTO) throws NoteNotExistsException {
        return this.noteService.update(noteDTO);
    }

    @PostMapping("/allPaginated")
    public Page getPaginated(@RequestBody PaginationInfo info){
        return this.noteService.getAllPaginated(info);
    }
}
