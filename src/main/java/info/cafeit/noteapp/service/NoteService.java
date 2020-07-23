package info.cafeit.noteapp.service;

import info.cafeit.noteapp.dto.NoteDto;
import info.cafeit.noteapp.entity.NoteEntity;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    Optional<NoteEntity> saveNote(NoteDto note);
    Optional<NoteEntity> updateNote(NoteDto note);
    boolean deleteNote(Integer id);
    Optional<NoteEntity> getNote(Integer id);
    List<NoteEntity> getNotes();
    List<NoteEntity> search(String keyword);
}
