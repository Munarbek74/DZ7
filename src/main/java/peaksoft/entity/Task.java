package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Session;
import org.hibernate.type.descriptor.java.DateJavaType;
import peaksoft.config.HibernateConfig;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "dead_line")
    private String deadLine;


    @ManyToOne(cascade = CascadeType.ALL)
    private Lesson lessons;

    public Task(String name, String deadLine) {
        this.name = name;
        this.deadLine = deadLine;

    }

    public Task(String name, String deadLine, Lesson lessons) {
        this.name = name;
        this.deadLine = deadLine;
        this.lessons = lessons;
    }

    public static void saveTask(Task task) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(task);

            session.getTransaction().commit();
            System.out.println("Task with id " + task.getId() + "successfully saved!");

        }

    }

    public static void updateTask(Task task) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Task task1 = session.find(Task.class, task.getId());
            session.merge(task);
            session.getTransaction().commit();
            System.out.println("Task witn name " + task1.getName() + " successfully update");
        }

    }
    public static void deleteTaskById(Long taskId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Task task = session.get(Task.class, taskId);
            session.delete(task);
            session.getTransaction().commit();

            System.out.println("Task with id: " + taskId + " successfully deleted!");
        }
    }}
