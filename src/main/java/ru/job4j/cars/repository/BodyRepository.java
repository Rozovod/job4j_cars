package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BodyRepository {
    private final CrudRepository crudRepository;

    public Optional<Body> findById(int id) {
        return crudRepository.optional(
                "from Body where id = :bId", Body.class,
                Map.of("bId", id)
        );
    }

    public List<Body> findByCategoryId(int categoryId) {
        return  crudRepository.query("SELECT b from Body b JOIN FETCH b.category с "
                        + "where с.id = :categoryId",
                Body.class, Map.of("categoryId", categoryId));
    }

    public List<Body> findAll() {
        return crudRepository.query("from Body", Body.class);
    }
}
