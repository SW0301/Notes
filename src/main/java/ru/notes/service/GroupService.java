package ru.notes.service;

import org.springframework.stereotype.Service;
import ru.notes.model.Group;

import java.util.List;

@Service
public interface GroupService {
    Group createGroup(String name);

    List<Group> getAllGroups();

    Group getGroupById(Long id);

    Group updateGroup(Long id, String name);

    String deleteGroup(Long id);
}
