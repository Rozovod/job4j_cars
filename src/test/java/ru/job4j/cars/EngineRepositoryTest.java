package ru.job4j.cars;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HbmTestConfiguration;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.EngineRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class EngineRepositoryTest {
    private final SessionFactory sf = HbmTestConfiguration.getSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);

    @Test
    public void whenSave() {
        Engine engine = new Engine();
        engine.setName("engine");
        engineRepository.save(engine);
        var rsl = engineRepository.findById(engine.getId()).get();
        assertThat(rsl).isEqualTo(engine);
    }
}
