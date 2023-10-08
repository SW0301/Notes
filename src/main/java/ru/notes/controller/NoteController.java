package ru.notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.*;


import ru.notes.model.Group;
import ru.notes.service.GroupService;
import ru.notes.service.NoteService;

@RestController
public class NoteController {
    private final NoteService noteService;
    private final GroupService groupService;

    @Autowired
    public NoteController(NoteService noteService, GroupService groupService) {
        this.noteService = noteService;
        this.groupService = groupService;
    }

    @PostMapping("/note")
    public ResponseEntity createNote(@RequestParam(name = "text") String text,
                                     @RequestParam(name = "is_italics", required = false, defaultValue = "false") boolean isItalics,
                                     @RequestParam(name = "is_bold", required = false, defaultValue = "false") boolean isBold,
                                     @RequestParam(name = "text_size", required = false, defaultValue = "11") double textSize,
                                     @RequestParam(name = "group_id", required = false, defaultValue = "1") Long groupId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(noteService.createNote(text, isItalics, isBold, textSize, groupId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Group is deleted or does not exist");
        }
    }

    @GetMapping("/note/{id}")
    public ResponseEntity getNote(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(noteService.getNoteById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Note does not exists");
        }
    }

    @GetMapping("/note")
    public ResponseEntity getAllNotes() {
        try {
            return ResponseEntity.ok(noteService.getAllNotes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something wrong");
        }
    }

    @PutMapping("/note/{id}")
    public ResponseEntity updateNote(@PathVariable Long id,
                                     @RequestParam(name = "text") String text,
                                     @RequestParam(name = "is_italics", required = false) boolean isItalics,
                                     @RequestParam(name = "is_bold", required = false) boolean isBold,
                                     @RequestParam(name = "text_size", required = false) double textSize,
                                     @RequestParam(name = "group_id", required = false) Long groupId) {
        try {
            return ResponseEntity.ok(noteService.updateNote(id, text, isItalics, isBold, textSize, groupId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cant find note");
        }
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity deleteNote(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(noteService.deleteNote(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cant find note");
        }
    }

}
