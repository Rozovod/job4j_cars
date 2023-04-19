package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class PostRepository {
    private final CrudRepository crudRepository;

    public List<Post> findFromLastDay() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        return crudRepository.query(
                "SELECT p FROM Post p WHERE p.created >= :yesterday",
                Post.class, Map.of("yesterday", yesterday));
    }

    public List<Post> findWithPhoto() {
        return crudRepository.query(
                "SELECT p FROM Post p JOIN FETCH p.files "
                        + "WHERE size(files) > 0", Post.class);
    }

    public List<Post> findByCarBrand(String brand) {
        return crudRepository.query(
                "SELECT p FROM Post p JOIN FETCH p.car WHERE p.car.name LIKE :brand",
                Post.class, Map.of("brand", brand));
    }
}
