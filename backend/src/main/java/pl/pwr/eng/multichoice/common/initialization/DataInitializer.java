package pl.pwr.eng.multichoice.common.initialization;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.pwr.eng.multichoice.domain.area.Area;
import pl.pwr.eng.multichoice.domain.area.AreaRepository;
import pl.pwr.eng.multichoice.domain.course.Course;
import pl.pwr.eng.multichoice.domain.course.CourseRepository;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    CourseRepository courseRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        init();
    }

    private void init() {
        Course course1 = new Course();
        course1.setName("Logika");

        Course course2 = new Course();
        course2.setName("Podstawy programowania");

        Area a1c1 = new Area();
        a1c1.setName("Podstawy");
        a1c1.setCourse(course1);

        Area a2c1 = new Area();
        a2c1.setName("Rachunek zdań");
        a2c1.setCourse(course1);

        Area a1c2 = new Area();
        a1c2.setName("Podstawy");
        a1c2.setCourse(course2);

        Area a2c2 = new Area();
        a2c2.setName("Pętle");
        a2c2.setCourse(course2);

        courseRepository.saveAll(Lists.newArrayList(course1, course2));
        areaRepository.saveAll(Lists.newArrayList(a1c1, a2c1, a1c2, a2c2));
    }

}