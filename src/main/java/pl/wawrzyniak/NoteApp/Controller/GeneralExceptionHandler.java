package pl.wawrzyniak.NoteApp.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(value = ObjectOptimisticLockingFailureException.class)
    public ResponseEntity versionConflict(){
        return new ResponseEntity(HttpStatus.CONFLICT);
    }
}
