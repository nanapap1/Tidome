package ru.MarkMoskvitin.Timedome.back.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.MarkMoskvitin.Timedome.back.entity.Subtask;

public interface SubtaskRepository extends JpaRepository<Subtask,Long> {
}
