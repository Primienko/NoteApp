package pl.wawrzyniak.NoteApp.Repository.CustomExeption;

public class EmptyPredicateExpetion extends Exception{
    public EmptyPredicateExpetion(String msg){
        super(msg);
    }
}
