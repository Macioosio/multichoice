package pl.pwr.eng.multichoice.domain.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Answer findById(UUID uuid);
    List<Answer> findByQuestionId(UUID uuid);
}