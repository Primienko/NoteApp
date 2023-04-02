package pl.wawrzyniak.NoteApp.Service.Verifier;

import org.springframework.stereotype.Service;
import pl.wawrzyniak.NoteApp.Repository.CustomExeption.EmptyNoteException;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteDTO;

@Service
public class EmptyNoteVerifier implements NoteVerifier{
    @Override
    public void verify(NoteDTO note) throws EmptyNoteException {
        String text = note.getText();
        if(text == null) {throw new EmptyNoteException();}
        if(text.trim().length() == 0) {
            throw new EmptyNoteException();
        }
    }
}
