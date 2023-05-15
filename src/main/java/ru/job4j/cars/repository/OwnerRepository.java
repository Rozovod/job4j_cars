package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OwnerRepository {
    private final CrudRepository crudRepository;

    public Owner save(Owner owner) {
        crudRepository.run(session -> session.save(owner));
        return owner;
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
                "from Owner where id = :oId", Owner.class, Map.of("oId", id));
    }

    public Optional<Owner> findByUser(User user) {
        return crudRepository.optional(
                "from Owner o JOIN FETCH o.user u where u = :user", Owner.class,
                Map.of("user", user)
        );
    }

    public List<Owner> findAll() {
        return crudRepository.query("from Owner", Owner.class);
    }
}
