package pl.pwr.eng.multichoice.domain.solution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.eng.multichoice.domain.answer.AnswerService;

import java.util.List;
import java.util.UUID;

@Service
public class SolutionService {

    @Autowired
    SolutionRepository solutionRepository;

    public List<Solution> findAll(){
        return solutionRepository.findAll();
    }

    public Solution findById(UUID uuid) {
        return solutionRepository.findById(uuid);
    }

    public void save(Solution solution) {
        solutionRepository.save(solution);
    }


    public void modifySolution(Solution modifiedSolution) {
        Solution originalSolution = findById(modifiedSolution.getId());
        originalSolution.setAnswers(modifiedSolution.getAnswers());
        originalSolution.setAuthor(modifiedSolution.getAuthor());
        originalSolution.setPoints(modifiedSolution.getPoints());
        originalSolution.setTest(modifiedSolution.getTest());
        save(originalSolution);
    }

    public void delete(Solution solution) {
        solutionRepository.delete(solution);
    }
}

