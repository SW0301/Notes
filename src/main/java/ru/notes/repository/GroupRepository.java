package ru.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.notes.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
