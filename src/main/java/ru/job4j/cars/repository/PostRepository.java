package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Category;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PostRepository {
    private final CrudRepository crudRepository;

    public Post save(Post post) {
        crudRepository.run(session -> session.save(post));
        return post;
    }

    public boolean update(Post post) {
        return crudRepository.booleanRun(session -> {
            session.merge(post);
            return true;
        });
    }

    public boolean delete(int id) {
        return crudRepository.booleanRun("DELETE Post WHERE id = :pId", Map.of("pId", id));
    }

    public Optional<Post> findById(int id) {
        return crudRepository.optional(
                "from Post where id = :pId", Post.class,
                Map.of("pId", id));
    }

    public List<Post> findAll() {
        return crudRepository.query("from Post p JOIN FETCH p.car JOIN FETCH p.files", Post.class);
    }

    public List<Post> findFromLastDay() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        return crudRepository.query(
                "SELECT p FROM Post p JOIN FETCH p.files WHERE p.created >= :yesterday",
                Post.class, Map.of("yesterday", yesterday));
    }

    public List<Post> findWithPhoto() {
        return crudRepository.query(
                "SELECT p FROM Post p JOIN FETCH p.files f "
                        + "WHERE size(f) > 0", Post.class);
    }

    public List<Post> findByCarBrand(String brand) {
        return crudRepository.query(
                "SELECT p FROM Post p JOIN FETCH p.car с WHERE с.name = :brand",
                Post.class, Map.of("brand", brand));
    }

    public List<Post> findByCategory(Category category) {
        return crudRepository.query(
                "SELECT p FROM Post p JOIN FETCH p.files JOIN FETCH p.car car WHERE car.category = :category",
                Post.class, Map.of("category", category)
        );
    }

    public List<Post> findByState(boolean state) {
        return crudRepository.query("from Post p JOIN FETCH p.files where carNew = :carNew", Post.class,
                Map.of("carNew", state));
    }

    public List<Post> findBySold(boolean carSold) {
        return crudRepository.query("from Post p JOIN FETCH p.files where carSold = :carSold", Post.class,
                Map.of("carSold", carSold));
    }

    public boolean updateStates(int id) {
        return crudRepository.booleanRun("UPDATE Post SET carSold = :carSold where id = :pId",
                Map.of("carSold", true, "pId", id));
    }
}
