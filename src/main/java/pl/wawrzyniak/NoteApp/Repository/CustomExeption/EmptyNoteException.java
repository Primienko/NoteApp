package pl.wawrzyniak.NoteApp.Repository.CustomExeption;

public class EmptyNoteException extends VerificationException{
    public EmptyNoteException(){
        super("Your note is empty");
    }
}
