package ru.MarkMoskvitin.Timedome.back.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.MarkMoskvitin.Timedome.back.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

}
