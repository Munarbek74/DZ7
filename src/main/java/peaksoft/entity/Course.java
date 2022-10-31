package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Session;
import peaksoft.config.HibernateConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courseses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "duration")
    private String duration;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "courses")
    private List<Instructor> instructors ;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "course")
    private List<Lesson> lesson;





    public Course(String courseName, String duration, String createAt, String imageLink, String description, List<Lesson> lesson) {
        this.courseName = courseName;
        this.duration = duration;
        this.createAt = createAt;
        this.imageLink = imageLink;
        this.description = description;
        this.lesson = lesson;
    }


    public Course(Long id, String courseName, String duration, String createAt, String imageLink, String description) {
        this.id = id;
        this.courseName = courseName;
        this.duration = duration;
        this.createAt = createAt;
        this.imageLink = imageLink;
        this.description = description;
    }

    public Course(String courseName, String duration, String createAt, String imageLink, String description) {
        this.courseName = courseName;
        this.duration = duration;
        this.createAt = createAt;
        this.imageLink = imageLink;
        this.description = description;
    }

    public static void saveCourse(Course course) {
    try (Session session = HibernateConfig.getSessionFactory().openSession()) {
        session.beginTransaction();
        session.save(course);
        session.getTransaction().commit();
        System.out.println("Course with name: " + course.getCourseName() + " successfully saved!");
}
    }

    public static Course getCourseById(Long courseId){
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Course course = session.get(Course.class, courseId);
            session.getTransaction().commit();
            if (course != null) {
                System.out.println("Course with id: " + courseId + " successfully found!");
            } else {
                System.out.println("We couldn't find course with id: " + courseId + "!");
            }
            return course;
        }
    }

    public static List<Course> getAllCourse() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<Course> course = session.createQuery("from Course order by createAt").getResultList();
            session.getTransaction().commit();

            System.out.println(course.size() + " course(s) have(has) been found!");

            return course;
        }
    }
    public static void updateCourse(Course course) {
    try (Session session = HibernateConfig.getSessionFactory().openSession()) {
         session.beginTransaction();
         Course course1 = session.find(Course.class, course.getId());
         session.merge(course);
        session.getTransaction().commit();
        System.out.println("Course with name " + course1.getCourseName() + " successfully update!");

    }
}

    public static void deleteCourseById(Long courseId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Course course = session.get(Course.class, courseId);
            session.delete(course);
            session.getTransaction().commit();

            System.out.println("Course with id: " + courseId + " successfully deleted!");
        }
    }

    public static List getCourseByName(String courseName) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Course  where courseName =: courseNameparam");
            query.setParameter("courseNameparam", courseName);
            List list = query.getResultList();
            session.getTransaction().commit();
            if (list != null) {
                System.out.println("Course with name: " + courseName + " successfully found!");
            } else {
                System.out.println("We couldn't find course with name: " + courseName + "!");
            }
            return list;
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", duration='" + duration + '\'' +
                ", createAt='" + createAt + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
