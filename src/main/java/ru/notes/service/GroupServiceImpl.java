package ru.notes.service;

import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.notes.model.Group;
import ru.notes.model.Note;
import ru.notes.repository.GroupRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final NoteService noteService;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, NoteServiceImpl noteService) {
        this.groupRepository = groupRepository;
        this.noteService = noteService;
    }

    @Override
    public Group createGroup(String name) {
        Group group = new Group();
        group.setName(name);
        group.setDeleted(false);
        groupRepository.save(group);
        return group;
    }

    @Override
    public List<Group> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        List<Group> notDeletedGroups = new ArrayList<>();
        for(Group g : groups) {
            if(!g.isDeleted())
                notDeletedGroups.add(g);
        }
        if(notDeletedGroups.size() != 0)
            return notDeletedGroups;
        else throw new EntityNotFoundException();
    }

    @Override
    public Group getGroupById(Long id) {
        try {
            Group group = groupRepository.getReferenceById(id);
            if(!group.isDeleted()) {
                return group;
            } else throw new EntityNotFoundException();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Group updateGroup(Long id, String name) {
        Group group = groupRepository.getReferenceById(id);
        group.setName(name);
        groupRepository.save(group);
        return group;
    }

    @Override
    public String deleteGroup(Long id) {
        try {
            Group group = groupRepository.getReferenceById(id);
            group.setDeleted(true);
            List<Note> noteList = noteService.getAllNotesInGroup(id);
            for (Note n : noteList) {
                noteService.deleteNote(n.getId());
            }
            groupRepository.save(group);

            return "OK";
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
