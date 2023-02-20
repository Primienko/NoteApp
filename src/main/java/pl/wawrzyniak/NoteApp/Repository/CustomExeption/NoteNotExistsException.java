package pl.wawrzyniak.NoteApp.Repository.CustomExeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoteNotExistsException extends Exception{
    public NoteNotExistsException(){
        super("Note that you are trying to find does not exists.");
    }
}
