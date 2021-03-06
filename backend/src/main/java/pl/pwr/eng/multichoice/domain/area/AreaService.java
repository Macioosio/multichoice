package pl.pwr.eng.multichoice.domain.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.eng.multichoice.domain.question.Question;
import pl.pwr.eng.multichoice.domain.question.QuestionService;

import java.util.List;
import java.util.UUID;

@Service
public class AreaService {

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    QuestionService questionService;

    public List<Area> findAll(){
        return areaRepository.findAll();
    }

    public Area findById(UUID uuid) {
        return areaRepository.findById(uuid);
    }

    public List<Area> findByCourseId(UUID uuid) {
        return areaRepository.findByCourseId(uuid);
    }

    public void save(Area area) {
        areaRepository.save(area);
    }

    public void modifyArea(Area modifiedArea) {
        Area originalArea = findById(modifiedArea.getId());
        originalArea.setName(modifiedArea.getName());
        originalArea.setCourse(modifiedArea.getCourse());
        save(originalArea);
    }

    public void delete(Area area) {
        areaRepository.delete(area);
    }

    public List<Question> getQuestions(Area area) {
        return questionService.findByAreaId(area.getId());
    }
}

