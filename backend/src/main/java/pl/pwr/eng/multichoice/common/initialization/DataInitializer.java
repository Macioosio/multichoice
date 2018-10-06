package pl.pwr.eng.multichoice.common.initialization;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import pl.pwr.eng.multichoice.domain.answer.Answer;
import pl.pwr.eng.multichoice.domain.answer.AnswerRepository;
import pl.pwr.eng.multichoice.domain.area.Area;
import pl.pwr.eng.multichoice.domain.area.AreaRepository;
import pl.pwr.eng.multichoice.domain.course.Course;
import pl.pwr.eng.multichoice.domain.course.CourseRepository;
import pl.pwr.eng.multichoice.domain.question.Question;
import pl.pwr.eng.multichoice.domain.question.QuestionRepository;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

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

        Question q1 = new Question();
        q1.setCourse(course1);
        q1.setArea(a1c1);
        q1.setContent("Pytanie pierwsze, proszę odpowiedzieć");

        Question q2 = new Question();
        q2.setCourse(course1);
        q2.setArea(a1c1);
        q1.setContent("Pytanie drugie, proszę odpowiedzieć");

        Question q3 = new Question();
        q3.setCourse(course1);
        q3.setArea(a1c1);
        q3.setContent("Pytanie trzecie, proszę odpowiedzieć");

        Question q4 = new Question();
        q4.setCourse(course1);
        q4.setArea(a1c1);
        q4.setContent("Pytanie czwarte, proszę odpowiedzieć");

        Answer an1q1 = new Answer();
        an1q1.setCorrect(true);
        an1q1.setQuestion(q1);
        an1q1.setContent("Prawda");

        Answer an2q1 = new Answer();
        an2q1.setContent("Fałsz");
        an2q1.setCorrect(false);
        an2q1.setQuestion(q1);

        Answer an3q1 = new Answer();
        an3q1.setContent("Fałsz");
        an3q1.setCorrect(false);
        an3q1.setQuestion(q1);

        Answer an1q2 = new Answer();
        an1q2.setCorrect(true);
        an1q2.setQuestion(q2);
        an1q2.setContent("Prawda");

        Answer an2q2 = new Answer();
        an2q2.setContent("Fałsz");
        an2q2.setCorrect(false);
        an2q2.setQuestion(q2);

        Answer an3q2 = new Answer();
        an3q2.setContent("Fałsz");
        an3q2.setCorrect(false);
        an3q2.setQuestion(q2);

        courseRepository.saveAll(Lists.newArrayList(course1, course2));
        areaRepository.saveAll(Lists.newArrayList(a1c1, a2c1, a1c2, a2c2));
        questionRepository.saveAll(Lists.newArrayList(q1, q2, q3, q4));
        answerRepository.saveAll(Lists.newArrayList(an1q1, an2q1, an3q1, an1q2, an2q2, an3q2));
    }

}