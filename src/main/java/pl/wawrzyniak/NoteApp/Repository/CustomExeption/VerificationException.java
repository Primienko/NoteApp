package pl.wawrzyniak.NoteApp.Repository.CustomExeption;

public class VerificationException extends Exception{
    VerificationException(String msg){
        super(msg);
    }
}
