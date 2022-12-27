package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

class PostRepositoryTest {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final PostRepository postRepository = new PostRepository(crudRepository);

    @Test
    public void whenAdd() {
        postRepository.deleteAll();
        Post post1 = new Post();
        Post post2 = new Post();
        Post post3 = new Post();
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        List<Post> rsl = postRepository.findAll();
        assertThat(3).isEqualTo(rsl.size());
    }

    @Test
    public void whenShowWithPhoto() {
        postRepository.deleteAll();
        Post post1 = new Post();
        post1.setPhoto(new byte[]{1, 2, 3});
        Post post2 = new Post();
        Post post3 = new Post();
        List<Post> actual = List.of(post1);
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        List<Post> rsl = postRepository.showWithPhoto();
        assertThat(actual.size()).isEqualTo(rsl.size());
    }

    @Test
    public void whenShowInDay() {
        postRepository.deleteAll();
        Post post1 = new Post();
        post1.setCreated(LocalDateTime.now());
        Post post2 = new Post();
        post2.setCreated(LocalDateTime.now());
        Post post3 = new Post();
        post3.setCreated(LocalDateTime.now().minusDays(2));
        List<Post> actual = List.of(post1, post2);
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        List<Post> rsl = postRepository.showInDay();
        assertThat(actual.size()).isEqualTo(rsl.size());
    }
}