package pl.pwr.eng.multichoice.common.initialization;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.pwr.eng.multichoice.domain.answer.Answer;
import pl.pwr.eng.multichoice.domain.answer.AnswerRepository;
import pl.pwr.eng.multichoice.domain.area.Area;
import pl.pwr.eng.multichoice.domain.area.AreaRepository;
import pl.pwr.eng.multichoice.domain.course.Course;
import pl.pwr.eng.multichoice.domain.course.CourseRepository;
import pl.pwr.eng.multichoice.domain.question.Question;
import pl.pwr.eng.multichoice.domain.question.QuestionRepository;
import pl.pwr.eng.multichoice.domain.student.Student;
import pl.pwr.eng.multichoice.domain.student.StudentRepository;
import pl.pwr.eng.multichoice.domain.teacher.Teacher;
import pl.pwr.eng.multichoice.domain.teacher.TeacherRepository;
import pl.pwr.eng.multichoice.domain.test.Test;
import pl.pwr.eng.multichoice.domain.test.TestRepository;
import pl.pwr.eng.multichoice.domain.test.grading.GradingMethod;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TestRepository testRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        init();
    }

    private void init() {
        Teacher teacher = new Teacher();
        teacher.setEmail("email@email.com");
        teacher.setPassword(bCryptPasswordEncoder.encode("password"));

        Student student = new Student();
        student.setEmail("student@email.com");
        student.setPassword(bCryptPasswordEncoder.encode("password"));

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
        q1.setPoints(2);

        Question q2 = new Question();
        q2.setCourse(course1);
        q2.setArea(a1c1);
        q2.setContent("Pytanie drugie, proszę odpowiedzieć");
        q2.setPoints(1);

        Question q3 = new Question();
        q3.setCourse(course2);
        q3.setArea(a1c2);
        q3.setContent("Pytanie trzecie, proszę odpowiedzieć");
        q3.setPoints(1);

        Question q4 = new Question();
        q4.setCourse(course2);
        q4.setArea(a1c2);
        q4.setContent("Pytanie czwarte, proszę odpowiedzieć");
        q4.setPoints(1);

        Question q5 = new Question();
        q5.setCourse(course1);
        q5.setArea(a1c1);
        q5.setContent("Pytanie piąte, proszę odpowiedzieć");
        q5.setPoints(2);

        Question q6 = new Question();
        q6.setCourse(course1);
        q6.setArea(a1c1);
        q6.setContent("Pytanie szóste, proszę odpowiedzieć");
        q6.setPoints(1);

        Question q7 = new Question();
        q7.setCourse(course2);
        q7.setArea(a1c2);
        q7.setContent("Pytanie siódme, proszę odpowiedzieć");
        q7.setPoints(1);

        Question q8 = new Question();
        q8.setCourse(course2);
        q8.setArea(a1c2);
        q8.setContent("Pytanie ósme, proszę odpowiedzieć");
        q8.setPoints(1);

        Answer an1q1 = new Answer();
        an1q1.setCorrect(true);
        an1q1.setQuestion(q1);
        an1q1.setContent("Prawda");
        an1q1.setPoints(1);

        Answer an2q1 = new Answer();
        an2q1.setContent("Prawda");
        an2q1.setCorrect(true);
        an2q1.setQuestion(q1);
        an2q1.setPoints(1);

        Answer an3q1 = new Answer();
        an3q1.setContent("Fałsz");
        an3q1.setCorrect(false);
        an3q1.setQuestion(q1);
        an3q1.setPoints(-1);

        Answer an1q2 = new Answer();
        an1q2.setCorrect(true);
        an1q2.setQuestion(q2);
        an1q2.setContent("Prawda");
        an1q2.setPoints(1);

        Answer an2q2 = new Answer();
        an2q2.setContent("Fałsz");
        an2q2.setCorrect(false);
        an2q2.setQuestion(q2);
        an2q2.setPoints(-1);

        Answer an3q2 = new Answer();
        an3q2.setContent("Fałsz");
        an3q2.setCorrect(false);
        an3q2.setQuestion(q2);
        an3q2.setPoints(-1);

        Answer an1q3 = new Answer();
        an1q3.setCorrect(true);
        an1q3.setQuestion(q3);
        an1q3.setContent("Prawda");
        an1q3.setPoints(1);

        Answer an2q3 = new Answer();
        an2q3.setContent("Fałsz");
        an2q3.setCorrect(false);
        an2q3.setQuestion(q3);
        an2q3.setPoints(-1);

        Answer an3q3 = new Answer();
        an3q3.setContent("Fałsz");
        an3q3.setCorrect(false);
        an3q3.setQuestion(q3);
        an2q3.setPoints(-1);

        Answer an1q4 = new Answer();
        an1q4.setCorrect(true);
        an1q4.setQuestion(q4);
        an1q4.setContent("Prawda");
        an1q4.setPoints(1);

        Answer an2q4 = new Answer();
        an2q4.setContent("Fałsz");
        an2q4.setCorrect(false);
        an2q4.setQuestion(q4);
        an2q4.setPoints(-1);

        Answer an3q4 = new Answer();
        an3q4.setContent("Fałsz");
        an3q4.setCorrect(false);
        an3q4.setQuestion(q4);
        an3q4.setPoints(-1);

        Answer an1q5 = new Answer();
        an1q5.setCorrect(true);
        an1q5.setQuestion(q5);
        an1q5.setContent("Prawda");
        an1q5.setPoints(1);

        Answer an2q5 = new Answer();
        an2q5.setContent("Prawda");
        an2q5.setCorrect(true);
        an2q5.setQuestion(q5);
        an2q5.setPoints(1);

        Answer an3q5 = new Answer();
        an3q5.setContent("Fałsz");
        an3q5.setCorrect(false);
        an3q5.setQuestion(q5);
        an3q5.setPoints(-1);

        Answer an1q6 = new Answer();
        an1q6.setCorrect(true);
        an1q6.setQuestion(q6);
        an1q6.setContent("Prawda");
        an1q6.setPoints(1);

        Answer an2q6 = new Answer();
        an2q6.setContent("Fałsz");
        an2q6.setCorrect(false);
        an2q6.setQuestion(q6);
        an2q6.setPoints(-1);

        Answer an3q6 = new Answer();
        an3q6.setContent("Fałsz");
        an3q6.setCorrect(false);
        an3q6.setQuestion(q6);
        an3q6.setPoints(-1);

        Answer an1q7 = new Answer();
        an1q7.setCorrect(true);
        an1q7.setQuestion(q7);
        an1q7.setContent("Prawda");
        an1q7.setPoints(1);

        Answer an2q7 = new Answer();
        an2q7.setContent("Fałsz");
        an2q7.setCorrect(false);
        an2q7.setQuestion(q7);
        an2q7.setPoints(-1);

        Answer an3q7 = new Answer();
        an3q7.setContent("Fałsz");
        an3q7.setCorrect(false);
        an3q7.setQuestion(q7);
        an3q7.setPoints(-1);

        Answer an1q8 = new Answer();
        an1q8.setCorrect(true);
        an1q8.setQuestion(q8);
        an1q8.setContent("Prawda");
        an1q8.setPoints(1);

        Answer an2q8 = new Answer();
        an2q8.setContent("Fałsz");
        an2q8.setCorrect(false);
        an2q8.setQuestion(q8);
        an2q8.setPoints(-1);

        Answer an3q8 = new Answer();
        an3q8.setContent("Fałsz");
        an3q8.setCorrect(false);
        an3q8.setQuestion(q8);
        an3q8.setPoints(-1);

        Date todayDate = new Date();
        Calendar todayCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+1"));
        todayCalendar.setTime(todayDate);
        int currentYear = todayCalendar.get(Calendar.YEAR);
        int currentMonth = todayCalendar.get(Calendar.MONTH);
        int currentDay = todayCalendar.get(Calendar.DAY_OF_MONTH);

        Calendar cal = Calendar.getInstance();

        Test test1 = new Test();
        cal.set(currentYear, currentMonth, currentDay, 8, 00, 00);
        test1.setStart(cal.getTime());
        cal.set(currentYear, currentMonth, currentDay, 24, 00, 00);
        test1.setEnd(cal.getTime());
        test1.setPassword("password");
        test1.setCourse(course1);
        test1.setStudents(Lists.newArrayList(student));
        test1.setQuestions(Lists.newArrayList(q1, q2, q3, q4, q5, q6, q7, q8));
        test1.setAuthor(teacher);
        test1.setPoints(10);
        test1.setGradingMethod(GradingMethod.DO);
        test1.setNavigable(true);

        Test test2 = new Test();
        cal.set(currentYear, currentMonth, currentDay, 9, 00, 00);
        test2.setStart(cal.getTime());
        cal.set(currentYear, currentMonth, currentDay, 24, 00, 00);
        test2.setEnd(cal.getTime());
        test2.setPassword("password");
        test2.setCourse(course1);
        test2.setStudents(Lists.newArrayList(student));
        test2.setQuestions(Lists.newArrayList(q1, q2, q3, q4, q5, q6, q7, q8));
        test2.setAuthor(teacher);
        test2.setPoints(10);
        test2.setGradingMethod(GradingMethod.CO);
        test2.setNavigable(true);

        Test test3 = new Test();
        cal.set(currentYear, currentMonth, currentDay, 10, 00, 00);
        test3.setStart(cal.getTime());
        cal.set(currentYear, currentMonth, currentDay, 24, 00, 00);
        test3.setEnd(cal.getTime());
        test3.setPassword("password");
        test3.setCourse(course1);
        test3.setStudents(Lists.newArrayList(student));
        test3.setQuestions(Lists.newArrayList(q1, q2, q3, q4, q5, q6, q7, q8));
        test3.setAuthor(teacher);
        test3.setPoints(10);
        test3.setGradingMethod(GradingMethod.TO);
        test3.setNavigable(true);

        Test test4 = new Test();
        cal.set(currentYear, currentMonth, currentDay, 11, 00, 00);
        test4.setStart(cal.getTime());
        cal.set(currentYear, currentMonth, currentDay, 24, 00, 00);
        test4.setEnd(cal.getTime());
        test4.setPassword("password");
        test4.setCourse(course1);
        test4.setStudents(Lists.newArrayList(student));
        test4.setQuestions(Lists.newArrayList(q1, q2, q3, q4, q5, q6, q7, q8));
        test4.setAuthor(teacher);
        test4.setPoints(10);
        test4.setGradingMethod(GradingMethod.TO_MINUS);
        test4.setNavigable(true);

        teacherRepository.save(teacher);
        studentRepository.save(student);
        courseRepository.saveAll(Lists.newArrayList(course1, course2));
        areaRepository.saveAll(Lists.newArrayList(a1c1, a2c1, a1c2, a2c2));
        questionRepository.saveAll(Lists.newArrayList(q1, q2, q3, q4, q5, q6, q7, q8));
        answerRepository.saveAll(Lists.newArrayList(
                an1q1, an2q1, an3q1,
                an1q2, an2q2, an3q2,
                an1q3, an2q3, an3q3,
                an1q4, an2q4, an3q4,
                an1q5, an2q5, an3q5,
                an1q6, an2q6, an3q6,
                an1q7, an2q7, an3q7,
                an1q8, an2q8, an3q8
        ));
        testRepository.saveAll(Lists.newArrayList(test1, test2, test3, test4));
    }

}