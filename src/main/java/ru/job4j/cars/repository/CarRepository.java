package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;

@Repository
@AllArgsConstructor
public class CarRepository {
    private final CrudRepository crudRepository;

    public List<Car> findAll() {
        return crudRepository.query("From Car", Car.class);
    }

    public void save(Car car) {
        crudRepository.run(session -> session.save(car));
    }
}
