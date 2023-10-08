package ru.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.notes.model.Note;

import java.util.List;
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query("SELECT n FROM Note AS n WHERE n.groupId.id = :groupId")
    List<Note> getAllNotesInGroup(@Param("groupId") Long groupId);

}
