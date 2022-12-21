package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;

import java.util.List;

@Repository
@AllArgsConstructor
public class DriverRepository {
    private final CrudRepository crudRepository;

    public List<Driver> findAll() {
        return crudRepository.query("From Driver", Driver.class);
    }

    public void save(Driver driver) {
        crudRepository.run(session -> session.save(driver));
    }

}
