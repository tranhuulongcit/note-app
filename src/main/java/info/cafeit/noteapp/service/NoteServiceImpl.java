package info.cafeit.noteapp.service;

import info.cafeit.noteapp.dto.NoteDto;
import info.cafeit.noteapp.entity.NoteEntity;
import info.cafeit.noteapp.repository.NoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    @Transactional
    public Optional<NoteEntity> saveNote(NoteDto note) {
        return Optional.of(noteRepository.save(note.toNote()));
    }

    @Override
    @Transactional
    public Optional<NoteEntity> updateNote(NoteDto note) {
        Optional<NoteEntity> noteUpdate = noteRepository.findById(note.getId());
        note.update(noteUpdate);
        return noteUpdate;
    }

    @Override
    public boolean deleteNote(Integer id) {
        try {
            noteRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    @Override
    public Optional<NoteEntity> getNote(Integer id) {
        return noteRepository.findById(id);
    }

    @Override
    public List<NoteEntity> getNotes() {
        return noteRepository.findAll();
    }

    @Override
    public List<NoteEntity> search(String keyword) {
        return noteRepository.search(keyword);
    }
}
