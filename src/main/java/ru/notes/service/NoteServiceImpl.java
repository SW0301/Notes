package ru.notes.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.notes.model.Group;
import ru.notes.model.Note;
import ru.notes.repository.NoteRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final GroupServiceImpl groupService;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, @Lazy GroupServiceImpl groupService) {
        this.noteRepository = noteRepository;
        this.groupService = groupService;
    }

    @Override
    public Note createNote(String text, boolean isItalic, boolean isBold, double textSize, Long groupId) {
        Note note = new Note();
        try {
            Group group = groupService.getGroupById(groupId);
            note.setGroupId(group);
            note.setText(text);
            note.setItalics(isItalic);
            note.setBold(isBold);
            note.setTextSize(textSize);
            return noteRepository.save(note);
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    @Override
    public Note getNoteById(Long id) {
        try {
            Note note = noteRepository.getReferenceById(id);
            if (!note.isDeleted())
                return note;
            else throw new EntityNotFoundException();
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    @Override
    public List<Note> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        List<Note> notDeletedNotes = new ArrayList<>();
        for (Note n : notes) {
            if (!n.isDeleted()) {
                notDeletedNotes.add(n);
            }
        }
        if (notDeletedNotes.size() != 0)
            return notDeletedNotes;
        else throw new EntityNotFoundException();
    }

    @Override
    public List<Note> getAllNotesInGroup(Long groupId) {
        return noteRepository.getAllNotesInGroup(groupId);
    }

    @Override
    public Note updateNote(Long id, String text, boolean isItalic, boolean isBold, double textSize, Long groupId) {
        try {
            Note note = noteRepository.getReferenceById(id);
            note.setText(text);
            if (isItalic != note.isItalics())
                note.setItalics(isItalic);
            if (isBold != note.isBold())
                note.setBold(isBold);
            note.setTextSize(textSize);
            note.setGroupId(groupService.getGroupById(groupId));
            noteRepository.save(note);
            return note;
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    @Override
    public String deleteNote(Long id) {
        try {
            Note note = noteRepository.getReferenceById(id);
            note.setDeleted(true);
            noteRepository.save(note);
            return "OK";
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

}
