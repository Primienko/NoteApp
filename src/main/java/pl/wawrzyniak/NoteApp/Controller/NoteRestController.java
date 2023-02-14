package pl.wawrzyniak.NoteApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteCriteriaDTO;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteDTO;
import pl.wawrzyniak.NoteApp.Service.NoteService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/Note/api/")
public class NoteRestController {
    private NoteService noteService;

    @Autowired
    public NoteRestController(NoteService noteService){
        this.noteService = noteService;
    }
    @PostMapping("add")
    public NoteDTO addNote(@RequestBody NoteDTO note){
        return this.noteService.save(note);
    }
    @GetMapping("all")
    public List<NoteDTO> getAllNotes(){
        return this.noteService.getAll();
    }
    @PostMapping("find")
    public List<NoteDTO> getByCriteria(@RequestBody NoteCriteriaDTO criteriaDTO){
        return this.noteService.getByCriteria(criteriaDTO);
    }
    @DeleteMapping("all")
    public ResponseEntity delateAll(){
        this.noteService.delateAll();
        return new ResponseEntity(HttpStatus.OK);
    }
}
