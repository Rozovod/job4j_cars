package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.File;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class FileRepository {
    private final CrudRepository crudRepository;

    public File save(File file) {
        crudRepository.run(session -> session.save(file));
        return file;
    }

    public Optional<File> findById(int id) {
        return crudRepository.optional(
                "from File where id = :fId", File.class,
                Map.of("fId", id));
    }

    public List<File> findAll() {
        return crudRepository.query("from File", File.class);
    }
}
