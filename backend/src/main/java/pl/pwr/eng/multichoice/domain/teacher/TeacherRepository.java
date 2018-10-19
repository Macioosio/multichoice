package pl.pwr.eng.multichoice.domain.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findById(UUID uuid);
}
