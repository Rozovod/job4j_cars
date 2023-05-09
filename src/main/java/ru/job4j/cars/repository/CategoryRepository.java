package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryRepository {
    private final CrudRepository crudRepository;

    public Optional<Category> findById(int id) {
        return crudRepository.optional(
                "from Category c JOIN FETCH c.bodies b where c.id = :cId", Category.class,
                Map.of("cId", id)
        );
    }

    public List<Category> findAll() {
        return crudRepository.query("from Category", Category.class);
    }
}
