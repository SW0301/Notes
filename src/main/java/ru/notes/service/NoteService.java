package ru.notes.service;

import org.springframework.stereotype.Service;
import ru.notes.model.Note;

import java.util.List;

@Service
public interface NoteService {
    Note createNote(String text, boolean isItalic, boolean isBold, double textSize, Long groupId);

    Note getNoteById(Long id);

    List<Note> getAllNotes();

    List<Note> getAllNotesInGroup(Long groupId);

    Note updateNote(Long id, String text, boolean isItalic, boolean isBold, double textSize, Long groupId);

    String deleteNote(Long id);

}
