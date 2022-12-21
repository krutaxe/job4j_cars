package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "driver")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "drivers")
    private List<Car> cars = new ArrayList<>();
}
