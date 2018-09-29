package pl.pwr.eng.multichoice.domain.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AreaService {

    @Autowired
    AreaRepository areaRepository;

    public List<Area> findAll(){
        return areaRepository.findAll();
    }

    public Area findById(UUID uuid) {
        return areaRepository.findById(uuid);
    }

    public void save(Area area) {
        areaRepository.save(area);
    }

    public void modifyArea(Area modifiedArea) {
        Area originalArea = findById(modifiedArea.getId());
        originalArea.setName(modifiedArea.getName());
        save(originalArea);
    }

    public void delete(Area area) {
        areaRepository.delete(area);
    }
}

