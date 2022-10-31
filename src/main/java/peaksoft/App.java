package peaksoft;

import org.hibernate.Session;
import peaksoft.config.HibernateConfig;
import peaksoft.entity.Course;
import peaksoft.entity.Instructor;
import peaksoft.entity.Lesson;
import peaksoft.entity.Task;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {


        Lesson lesson = new Lesson("Bank", "http://kkk");
        Lesson lesson1 = new Lesson("Mathem", "http://kdfs1");
        Lesson lesson2 = new Lesson("Econom", "http://khjkg");
        Lesson lesson3 = new Lesson("Finance", "http://6545");

        List<Lesson> lessons = new ArrayList<>(Arrays.asList(lesson,lesson1));
        List<Lesson> lessons1 = new ArrayList<>(Arrays.asList(lesson2,lesson3));


        Course course = new Course("Economic", " 5 years", "Lesson", "https://bla", "new course", lessons);
        Course course1 = new Course("Finance", " 4 years", "Begin", "https://byta", "new course", lessons1);
        List<Course> courseList = new ArrayList<>(Arrays.asList(course,course1));

        lesson.setCourse(course);
        lesson1.setCourse(course);
        lesson2.setCourse(course1);
        lesson3.setCourse(course1);


        Course.saveCourse(course);
        Course.saveCourse(course1);
        Task task = new Task("Number 1", "01-11-2022", lesson);
        Task task1 = new Task("Number 2", "01-06-2022", lesson);
        Task task2 = new Task("Number 3", "01-02-2024", lesson1);
        Task task3 = new Task("Number 4", "01-05-2021", lesson1);

        Task.saveTask(task);
        Task.saveTask(task1);
        Task.saveTask(task2);
        Task.saveTask(task3);


    Course.getAllCourse();
    Course course2 = new Course(3L, "Finance", "1 years", "Begin", "Http://3654", "Bla");

    Course.updateCourse(course2);
        Course.deleteCourseById(2l);
        Course.getCourseByName("Finance");


        Instructor instructor = new Instructor("Uson", "Usonov", "Uson@gmail.com","0-777-77-55-88", courseList);
        Instructor.saveInstructor(instructor);
        Instructor.getInstructorById(1l);

        Instructor instructor1 = new Instructor(2l,"Asan", "Asanov", "asan@gmail.com","0-555-78-96-45");
        Instructor.updateInstructor(instructor1);
        Instructor.getInstuctorByCourseId(1l);



        Lesson lesson8 = new Lesson("Bank", "http://kkk");
        Course course8 = new Course("111Finance", " 4 years", "Begin", "https://byta", "new course");
        lesson.setCourse(course8);
        Course.saveCourse(course8);
        Lesson.saveLesson(lesson8);


        HibernateConfig.shutDown();
    }




    }



