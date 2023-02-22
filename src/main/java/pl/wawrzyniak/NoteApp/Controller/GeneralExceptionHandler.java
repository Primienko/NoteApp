package pl.wawrzyniak.NoteApp.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.wawrzyniak.NoteApp.Repository.CustomExeption.EmptyNoteException;
import pl.wawrzyniak.NoteApp.Repository.CustomExeption.EmptyPredicateException;
import pl.wawrzyniak.NoteApp.Repository.CustomExeption.NoteNotExistsException;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(value = ObjectOptimisticLockingFailureException.class)
    public ResponseEntity versionConflict(){
        return new ResponseEntity(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = NoteNotExistsException.class)
    public ResponseEntity missingNote(){
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EmptyNoteException.class)
    public ResponseEntity emptyNote(){
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EmptyPredicateException.class)
    public ResponseEntity noCriteria(){
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
