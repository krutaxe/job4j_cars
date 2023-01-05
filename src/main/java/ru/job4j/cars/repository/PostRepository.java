package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
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
        return crudRepository.query("From Post where photo is not null", Post.class);
    }

    public List<Post> showByCarName(Car car) {
        return crudRepository.query("From Post p where p.car_id = :fId", Post.class,
                Map.of("fId", car.getId()));
    }
}
