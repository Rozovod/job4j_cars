package ru.job4j.cars;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HbmTestConfiguration;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest {
    private final SessionFactory sf = HbmTestConfiguration.getSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final UserRepository userRepository = new UserRepository(crudRepository);

    @Test
    public void whenSaveThenSame() {
        User user = new User();
        userRepository.create(user);
        User rsl = userRepository.findById(user.getId()).get();
        assertThat(rsl).isEqualTo(user);
    }

    @Test
    public void whenUpdateThenNew() {
        User user = new User();
        user.setLogin("login");
        userRepository.create(user);
        user.setLogin("newLogin");
        userRepository.update(user);
        var rsl = userRepository.findById(user.getId()).get();
        assertThat(rsl.getLogin()).isEqualTo("newLogin");
    }

    @Test
    public void whenDeleteThenEmpty() {
        User user = new User();
        userRepository.create(user);
        userRepository.delete(user.getId());
        var rsl = userRepository.findById(user.getId());
        assertThat(rsl).isEqualTo(Optional.empty());
    }

    @Test
    public void whenFindAllThenList() {
        User userFirst = new User();
        User userSecond = new User();
        userRepository.create(userFirst);
        userRepository.create(userSecond);
        var rsl = userRepository.findAllOrderById();
        assertThat(rsl).isEqualTo(List.of(userFirst, userSecond));
    }

    @Test
    public void whenFindByLoginAndPassword() {
        User userFirst = new User();
        userFirst.setLogin("login1");
        userFirst.setPassword("password1");
        User userSecond = new User();
        userSecond.setLogin("login2");
        userSecond.setPassword("password2");
        userRepository.create(userFirst);
        userRepository.create(userSecond);
        var rsl = userRepository.findByLoginAndPassword(
                userSecond.getLogin(), userSecond.getPassword()).get();
        assertThat(rsl).isEqualTo(userSecond);
    }

    @Test
    public void whenFindByLikeLoginThenList() {
        User userFirst = new User();
        userFirst.setLogin("login1");
        User userSecond = new User();
        userSecond.setLogin("login2");
        userRepository.create(userFirst);
        userRepository.create(userSecond);
        var rsl = userRepository.findByLikeLogin("login");
        assertThat(rsl).isEqualTo(List.of(userFirst, userSecond));
    }
}
