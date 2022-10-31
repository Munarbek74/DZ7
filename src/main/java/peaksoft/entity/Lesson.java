package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Session;
import peaksoft.config.HibernateConfig;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private  String name;

    @Column(name = "video_link")
    private String videoLink;

//    @Column(name = "course_id")
//    private int course_id;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Course course;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "lessons")
    private List<Task> tasks;



    public Lesson(String name, String videoLink) {
        this.name = name;
        this.videoLink = videoLink;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", videoLink='" + videoLink + '\'' +
                '}';
    }

    public static void saveLesson(Lesson lesson) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(lesson);
            session.getTransaction().commit();
            System.out.println("Lesson with name: " + lesson.getName() + " successfully saved!");

        }


    }

    public static void updateLesson(Lesson lesson) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Lesson lesson1 = session.find(Lesson.class, lesson.getId());
            session.merge(lesson);
            session.getTransaction().commit();
            System.out.println("Lesson with name " + lesson1.getName() + " successfully update!");

        }

    }

    public static Lesson getLessonById(Long lessonId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Lesson lesson = session.get(Lesson.class, lessonId);

            session.getTransaction().commit();
            if (lesson != null) {
                System.out.println("Lesson with id: " + lessonId + " successfully found!");
            } else {
                System.out.println("We couldn't find lesson with id: " + lessonId + "!");
            }
            return lesson;
        }

    }

}
