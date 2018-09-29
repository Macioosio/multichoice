package pl.pwr.eng.multichoice.domain

import groovy.json.JsonSlurper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.pwr.eng.multichoice.domain.course.Course
import pl.pwr.eng.multichoice.domain.course.CourseController

import pl.pwr.eng.multichoice.domain.course.CourseService
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.http.HttpStatus.*

class CourseControllerTest extends Specification {

    private CourseController courseController
    private CourseService courseService
    private Course course
    MockMvc mockMvc

    void setup() {
        courseController = new CourseController()
        courseService = Mock(CourseService.class)
        courseController.courseService = courseService
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build()

        course = new Course()
        course.id = UUID.randomUUID()
        course.name = "Test Course"
        courseService.save(course)
    }

    def "get all courses"() {
        when:
            def response = mockMvc.perform(get("/api/courses"))
        then:
            1 * courseService.findAll()
            response.andExpect(status().isOk())
    }

    def "get by id"() {
        when:
            def response = mockMvc.perform(get("/api/courses/" + course.id)).andReturn().response
            def content = new JsonSlurper().parseText(response.contentAsString)
        then:
            1 * courseService.findById(course.id) >> course
            response.status == OK.value()
            content.name == ("Test Course")

    }

    def "delete"() {
        when:
            def response = mockMvc.perform(delete("/api/courses/" + course.id))
        then:
            1 * courseService.findById(course.id) >> course
            response.andExpect(status().isOk())
    }

    def "post"(){
        given:
            Map request = [
                    name : "New Course"
            ]
        when:
            def response = mockMvc.perform(post("/api/courses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(request)))
                    .andReturn().response
        then:
            response.status == OK.value()
    }
    def "put"(){
        given:
        Map request = [
                id : course.id,
                name : "Modified Course"
        ]
        when:
        def response = mockMvc.perform(put("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andReturn().response
        then:
        response.status == OK.value()
    }

}
