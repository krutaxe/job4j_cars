package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "engine")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String name;

    @OneToOne(mappedBy = "engine")
    private Car car;
}
