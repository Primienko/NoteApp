package pl.wawrzyniak.NoteApp.Repository.CustomExeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmptyNoteException extends VerificationException{
    public EmptyNoteException(){
        super("Your note is empty");
    }
}
