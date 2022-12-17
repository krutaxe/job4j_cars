package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "price_history")
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int before;

    private int after;

    @Temporal(TemporalType.TIMESTAMP)
    private Date create;
}
