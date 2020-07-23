package info.cafeit.noteapp.repository;

import info.cafeit.noteapp.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Integer> {

    @Query("Select n from NoteEntity n where lower(n.title) like %:keyword% or lower(n.content) like %:keyword% order by n.title asc")
    List<NoteEntity> search(String keyword);
}
