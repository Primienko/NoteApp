package pl.wawrzyniak.NoteApp.Service.Verifier;

import pl.wawrzyniak.NoteApp.Repository.CustomExeption.EmptyNoteException;
import pl.wawrzyniak.NoteApp.Repository.CustomExeption.VerificationException;
import pl.wawrzyniak.NoteApp.Service.DTO.NoteDTO;

public interface NoteVerifier {
    void verify(NoteDTO note) throws VerificationException;
}
