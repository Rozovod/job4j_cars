package ru.job4j.cars;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HbmTestConfiguration;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.EngineRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class EngineRepositoryTest {
    private final SessionFactory sf = HbmTestConfiguration.getSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);

    @Test
    public void whenSaveThenSame() {
        Engine engine = new Engine();
        engineRepository.save(engine);
        var rsl = engineRepository.findById(engine.getId()).get();
        assertThat(rsl).isEqualTo(engine);
    }

    @Test
    public void whenUpdateThenNew() {
        Engine engine = new Engine();
        engine.setName("name");
        engineRepository.save(engine);
        engine.setName("newName");
        engineRepository.update(engine);
        var rsl = engineRepository.findById(engine.getId()).get();
        assertThat(rsl.getName()).isEqualTo("newName");
    }

    @Test
    public void whenDeleteThenEmpty() {
        Engine engine = new Engine();
        engineRepository.save(engine);
        engineRepository.delete(engine.getId());
        var rsl = engineRepository.findById(engine.getId());
        assertThat(rsl).isEqualTo(Optional.empty());
    }

    @Test
    public void whenFindAllThenList() {
        Engine engineFirst = new Engine();
        Engine engineSecond = new Engine();
        engineRepository.save(engineFirst);
        engineRepository.save(engineSecond);
        var rsl = engineRepository.findAll();
        assertThat(rsl).isEqualTo(List.of(engineFirst, engineSecond));
    }
}
