package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class PostRepository {
    private final CrudRepository crudRepository;

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public List<Post> findAll() {
        return crudRepository.query("From Post", Post.class);
    }

    public void save(Post post) {
        crudRepository.run(session -> session.save(post));
    }

    public List<Post> showInDay() {
       LocalDateTime now = LocalDateTime.now();
       LocalDateTime before = LocalDateTime.now().minusDays(1);
       return crudRepository.query(
               "From Post where created between :fBefore and :fNow", Post.class,
               Map.of("fBefore", before, "fNow", now));
    }

    public List<Post> showWithPhoto() {
        return crudRepository.query("From Post where length(photo) > 0", Post.class);
    }

    public List<Post> showByCarName(Car car) {
        return crudRepository.query("From Post p where p.car_id = :fId", Post.class,
                Map.of("fId", car.getId()));
    }

    public void deleteAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from Post ").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
