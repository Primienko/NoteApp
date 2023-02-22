package pl.wawrzyniak.NoteApp.Repository.CustomExeption;

public class NoteNotExistsException extends Exception{
    public NoteNotExistsException(){
        super("Note that you are trying to find does not exists.");
    }
}
