package pl.wawrzyniak.NoteApp.Repository.CustomExeption;

public class EmptyPredicateException extends Exception{
    public EmptyPredicateException(String msg){
        super(msg);
    }
}
