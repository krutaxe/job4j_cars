package ru.job4j.cars.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "auto_post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String name;

    private String text;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime created  = LocalDateTime.now();

    private int price = 100;

    private boolean status = false;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}
