package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarRepository {
    private final CrudRepository crudRepository;

    public Car save(Car car) {
        crudRepository.run(session -> session.save(car));
        return car;
    }

    public boolean update(Car car) {
        return crudRepository.booleanRun(session -> {
            session.merge(car);
            return true;
        });
    }

    public boolean delete(int id) {
        return crudRepository.booleanRun("DELETE Car WHERE id = :cId", Map.of("cId", id));
    }

    public Optional<Car> findById(int id) {
        return crudRepository.optional(
                "from Car where id = :cId", Car.class,
                Map.of("cId", id));
    }

    public List<Car> findAll() {
        return crudRepository.query("from Car", Car.class);
    }
}
