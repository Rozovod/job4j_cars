package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepository {
    private final CrudRepository crudRepository;
    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public Optional<User> create(User user) {
        Optional<User> userOptional = Optional.empty();
        try {
            crudRepository.run(session -> session.save(user));
            userOptional = Optional.of(user);
        } catch (Exception e) {
            LOG.error("Пользователь с таким логином уже существует", e);
        }
        return userOptional;

    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public boolean update(User user) {
        return crudRepository.booleanRun(session -> {
            session.merge(user);
            return true;
        });
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public boolean delete(int userId) {
        return crudRepository.booleanRun(
                "DELETE User WHERE id = :uId", Map.of("uId", userId));
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        return crudRepository.query("from User as u order by u.id", User.class);
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        return crudRepository.optional(
                "from User where id = :uId", User.class, Map.of("uId", id)
        );
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        return crudRepository.query(
                "from User where login like :uKey", User.class,
                Map.of("uKey", "%" + key + "%")
        );

    }

    /**
     * Найти пользователя по login и password.
     * @param login login.
     * @param password password.
     * @return Optional or user.
     */
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "from User as u where u.login = :uLogin and u.password = :uPassword", User.class,
                Map.of("uLogin", login, "uPassword", password)
        );
    }
}
