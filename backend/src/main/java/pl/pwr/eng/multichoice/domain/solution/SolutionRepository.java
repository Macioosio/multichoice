package pl.pwr.eng.multichoice.domain.solution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.eng.multichoice.domain.test.Test;

import java.util.List;
import java.util.UUID;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    Solution findById(UUID uuid);

    List<Solution> findAllByTest(Test test);
}