package ru.job4j.cars.model;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "engine")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
}
