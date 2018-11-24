package pl.pwr.eng.multichoice.domain

import pl.pwr.eng.multichoice.domain.area.Area
import pl.pwr.eng.multichoice.domain.area.AreaRepository
import pl.pwr.eng.multichoice.domain.area.AreaService
import pl.pwr.eng.multichoice.domain.course.Course
import spock.lang.Specification

class AreaServiceTest extends Specification {

    private AreaService areaService;
    private AreaRepository areaRepository;
    private Area area;

    void setup() {
        areaService = new AreaService()
        areaRepository = Mock(AreaRepository.class)
        areaService.areaRepository = areaRepository

        area = new Area()
        area.id = UUID.randomUUID()
        area.name = "Test Area"
        areaService.save(area)
    }

    def "Find All"() {
        when:
            areaService.findAll()
        then:
            1 * areaRepository.findAll()
    }

    def "Find by Id"() {
        when:
            areaService.findById(area.id)
        then:
            1 * areaRepository.findById(area.id)
    }

    def "Save"() {
        when:
            areaService.save(area)
        then:
            1 * areaRepository.save(area)
    }

    def "Modify area"(){
        given:
            Area modifiedArea = new Area(area.id, area.name, new Course(UUID.randomUUID(),"name"))
            modifiedArea.name = "Modified Area"
        when:
            areaRepository.findById(modifiedArea.id) >> area
            areaService.modifyArea(modifiedArea)
        then:
            area.name.is(modifiedArea.name)
            1 * areaRepository.save(area)

    }

    def "Delete"() {
        when:
            areaService.delete(area)
        then:
            1 * areaRepository.delete(area)
    }

}
