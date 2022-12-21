package ru.job4j.cars.repository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;

@Repository
@AllArgsConstructor
public class EngineRepository {
    private final CrudRepository crudRepository;

    public List<Engine> findAll() {
        return crudRepository.query("From Engine", Engine.class);
    }

    public void save(Engine engine) {
        crudRepository.run(session -> session.save(engine));
    }
}
