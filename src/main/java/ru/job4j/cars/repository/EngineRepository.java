package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class EngineRepository {
    private final CrudRepository crudRepository;

    public void save(Engine engine) {
        crudRepository.run(session -> session.persist(engine));
    }

    public boolean update(Engine engine) {
        return crudRepository.booleanRun(session -> {
            session.merge(engine);
            return true;
        });
    }

    public boolean delete(int id) {
        return crudRepository.booleanRun("DELETE Engine WHERE id = :eId", Map.of("eId", id));
    }

    public Optional<Engine> findById(int id) {
        return crudRepository.optional(
                "from Engine where id = :eId", Engine.class, Map.of("eId", id));
    }

    public List<Engine> findAll() {
        return crudRepository.query("from Engine", Engine.class);
    }
}
