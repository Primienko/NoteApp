package pl.wawrzyniak.NoteApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteDTO;
import pl.wawrzyniak.NoteApp.Service.NoteService;

@RestController
@RequestMapping("/Note/api")
public class NoteRestController {
    private NoteService noteService;

    @Autowired
    public NoteRestController(NoteService noteService){
        this.noteService = noteService;
    }
    @PostMapping("/add")
    public NoteDTO addNote(@RequestBody NoteDTO note){
        return this.noteService.save(note);
    }
}
