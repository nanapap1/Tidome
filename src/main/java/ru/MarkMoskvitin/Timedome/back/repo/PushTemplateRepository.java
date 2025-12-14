package ru.MarkMoskvitin.Timedome.back.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.MarkMoskvitin.Timedome.back.entity.PushTemplate;

public interface PushTemplateRepository extends JpaRepository<PushTemplate,Long> {
}
