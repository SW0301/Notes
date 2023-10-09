package ru.notes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.notes.service.GroupServiceImpl;

@RestController
public class GroupController {

    private final GroupServiceImpl groupService;

    public GroupController(GroupServiceImpl groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/group")
    public ResponseEntity getAllGroups() {
        try {
            return ResponseEntity.ok(groupService.getAllGroups());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Something Wrong");
        }
    }

    @PostMapping("/group")
    public ResponseEntity createGroup(@RequestParam(name = "name") String name) {
        try {
            return ResponseEntity.ok(groupService.createGroup(name));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Cant create group");
        }
    }

    @PutMapping("/group/{id}")
    public ResponseEntity updateGroup(@PathVariable Long id, @RequestParam(name = "name") String name) {
        try {
            return ResponseEntity.ok(groupService.updateGroup(id, name));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Cant find this group");
        }
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity deleteGroup(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(groupService.deleteGroup(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Cant find this group");
        }
    }
}
