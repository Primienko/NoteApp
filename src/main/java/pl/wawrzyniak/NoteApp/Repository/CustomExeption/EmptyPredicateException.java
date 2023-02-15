package pl.wawrzyniak.NoteApp.Repository.CustomExeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmptyPredicateException extends Exception{
    public EmptyPredicateException(String msg){
        super(msg);
    }
}
