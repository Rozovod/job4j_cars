package ru.job4j.cars;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HbmTestConfiguration;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.repository.CarRepository;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.EngineRepository;
import ru.job4j.cars.repository.OwnerRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class CarRepositoryTest {

    private final SessionFactory sf = HbmTestConfiguration.getSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);
    private final OwnerRepository ownerRepository = new OwnerRepository(crudRepository);
    private final CarRepository carRepository = new CarRepository(crudRepository);

    /* @AfterEach
    public void wipeTable() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM Engine").executeUpdate();
            session.createQuery("DELETE FROM Owner").executeUpdate();
            session.createQuery("DELETE FROM Car").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    } */

    @Test
    public void whenSave() {
        Engine engine = new Engine();
        engine.setName("engine");
        engineRepository.save(engine);
        Owner owner = new Owner();
        owner.setName("owner");
        ownerRepository.save(owner);
        Car car = new Car();
        car.setName("car");
        car.setEngine(engine);
        car.setOwner(owner);
        carRepository.save(car);
        Car rsl = carRepository.findById(car.getId()).get();
        assertThat(rsl).isEqualTo(car);
    }
}
