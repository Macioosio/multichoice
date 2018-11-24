package pl.pwr.eng.multichoice.domain

import groovy.json.JsonSlurper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.pwr.eng.multichoice.domain.area.Area
import pl.pwr.eng.multichoice.domain.area.AreaController
import pl.pwr.eng.multichoice.domain.area.AreaService
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AreaControlerTest extends Specification {

    private AreaController areaController
    private AreaService areaService
    private Area area
    MockMvc mockMvc

    void setup() {
        areaController = new AreaController()
        areaService = Mock(AreaService.class)
        areaController.areaService = areaService
        mockMvc = MockMvcBuilders.standaloneSetup(areaController).build()

        area = new Area()
        area.id = UUID.randomUUID()
        area.name = "Test Area"
        areaService.save(area)
    }

    def "get all areas"() {
        when:
        def response = mockMvc.perform(get("/api/areas"))
        then:
        1 * areaService.findAll()
        response.andExpect(status().isOk())
    }

    def "get by id"() {
        when:
        def response = mockMvc.perform(get("/api/areas/" + area.id)).andReturn().response
        def content = new JsonSlurper().parseText(response.contentAsString)
        then:
        1 * areaService.findById(area.id) >> area
        response.status == OK.value()
        content.name == ("Test Area")

    }

    def "delete"() {
        when:
        def response = mockMvc.perform(delete("/api/areas/" + area.id))
        then:
        1 * areaService.findById(area.id) >> area
        response.andExpect(status().isOk())
    }

    def "post"(){
        given:
        Map request = [
                name : "New Area"
        ]
        when:
        def response = mockMvc.perform(post("/api/areas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andReturn().response
        then:
        response.status == OK.value()
    }
    def "put"(){
        given:
        Map request = [
                id : area.id,
                name : "Modified Area"
        ]
        when:
        def response = mockMvc.perform(put("/api/areas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andReturn().response
        then:
        response.status == OK.value()
    }

}
