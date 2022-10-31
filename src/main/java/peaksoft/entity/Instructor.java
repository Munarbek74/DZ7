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
@ToString
@Entity
@Table(name = "instructors")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_namber")
    private String phoneNumber;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "course_instr",
    joinColumns = @JoinColumn(name = "course_id"),
    inverseJoinColumns = @JoinColumn(name = "instructor_id"))
    private List<Course> courses;

    public Instructor(String firstName, String lastName, String email, String phoneNumber, List<Course> courses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.courses = courses;
    }


    public Instructor(Long id, String firstName, String lastName, String email, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public static void saveInstructor(Instructor instructor) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(instructor);
            session.getTransaction().commit();
            System.out.println("Instructor with name: " + instructor.getFirstName() + " successfully saved!");
        }
    }

    public static void updateInstructor(Instructor instructor) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Instructor instructor1 = session.find(Instructor.class, instructor.getId());
            session.merge(instructor);
            session.getTransaction().commit();
            System.out.println("Instructor with name " + instructor1.getFirstName() + " successfully update!");

        }

    }

    public static Instructor getInstructorById(Long instId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Instructor instructor = session.get(Instructor.class, instId);

            session.getTransaction().commit();
            if (instructor != null) {
                System.out.println("Instructor with id: " + instId + " successfully found!");
            } else {
                System.out.println("We couldn't find instructor with id: " + instId + "!");
            }
            return instructor;
        }

        }
    public static List<Instructor> getInstuctorByCourseId(Long courseId){
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Course as s INNER JOIN FETCH s.instructors as ss WITH ss.id =: isParam");
            query.setParameter("isParam", courseId);
            query.executeUpdate();
            List<Instructor> instructors = query.getResultList();
            if (instructors != null) {
                System.out.println("Instructor with courseid: " + courseId + " successfully found!");
            } else {
                System.out.println("We couldn't find instructor with courseid: " + courseId + "!");
            }session.getTransaction().commit();
            return instructors;


        }
        }

    public static void deleteInstructorById(Long instructorId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Instructor instructor = session.get(Instructor.class, instructorId);
            session.delete(instructor);
            session.getTransaction().commit();

            System.out.println("Instructor with id: " + instructorId + " successfully deleted!");
        }
    }


    }


