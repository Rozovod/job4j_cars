package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.File;

@Repository
@AllArgsConstructor
public class FileRepository {
    private final CrudRepository crudRepository;

    public File save(File file) {
        crudRepository.run(session -> session.save(file));
        return file;
    }
}
