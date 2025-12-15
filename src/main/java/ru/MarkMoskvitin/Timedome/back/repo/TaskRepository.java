package ru.MarkMoskvitin.Timedome.back.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.MarkMoskvitin.Timedome.back.entity.Task;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task,Long> {
    @Query("FROM Task WHERE user.username = :username")
    List<Task> findByUser(String username);
}
