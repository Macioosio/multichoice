package pl.pwr.eng.multichoice.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findById(UUID uuid);

    List<Question> findByAreaId(UUID id);
}