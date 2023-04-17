package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OwnerRepository {
    private final CrudRepository crudRepository;

    public void save(Owner owner) {
        crudRepository.run(session -> session.persist(owner));
    }

    public boolean update(Owner owner) {
        return crudRepository.booleanRun(session -> {
            session.merge(owner);
            return true;
        });
    }

    public boolean delete(int id) {
        return crudRepository.booleanRun("DELETE Owner WHERE id = :oId", Map.of("oId", id));
    }

    public Optional<Owner> findById(int id) {
        return crudRepository.optional(
                "from Owner o JOIN FETCH o.user where o.id = :oId", Owner.class, Map.of("oId", id));
    }

    public List<Owner> findAll() {
        return crudRepository.query("from Owner", Owner.class);
    }
}
