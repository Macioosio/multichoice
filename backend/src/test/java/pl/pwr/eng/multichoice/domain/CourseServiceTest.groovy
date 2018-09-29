package pl.pwr.eng.multichoice.domain

import pl.pwr.eng.multichoice.domain.course.Course
import pl.pwr.eng.multichoice.domain.course.CourseRepository
import pl.pwr.eng.multichoice.domain.course.CourseService
import spock.lang.Specification

class CourseServiceTest extends Specification {

    private CourseService courseService;
    private CourseRepository courseRepository;
    private Course course;

    void setup() {
        courseService = new CourseService()
        courseRepository = Mock(CourseRepository.class)
        courseService.courseRepository = courseRepository

        course = new Course()
        course.id = UUID.randomUUID()
        course.name = "Test Course"
        courseService.save(course)
    }

    def "Find All"() {
        when:
            courseService.findAll()
        then:
            1 * courseRepository.findAll()
    }

    def "Find by Id"() {
        when:
            courseService.findById(course.id)
        then:
            1 * courseRepository.findById(course.id)
    }

    def "Save"() {
        when:
            courseService.save(course)
        then:
            1 * courseRepository.save(course)
    }

    def "Modify cinema"(){
        given:
            Course modifiedCourse = new Course(course.id, course.name)
            modifiedCourse.name = "Modified Course"
        when:
            courseRepository.findById(modifiedCourse.id) >> course
            courseService.modifyCourse(modifiedCourse)
        then:
            course.name.is(modifiedCourse.name)
            1 * courseRepository.save(course)

    }

    def "Delete"() {
        when:
            courseService.delete(course)
        then:
            1 * courseRepository.delete(course)
    }

}
