package ru.job4j.cars;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HbmTestConfiguration;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CarRepositoryTest {

    private final SessionFactory sf = HbmTestConfiguration.getSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final CarRepository carRepository = new CarRepository(crudRepository);

    @Test
    public void whenSaveThenSame() {
        Car car = new Car();
        carRepository.save(car);
        var rsl = carRepository.findById(car.getId()).get();
        assertThat(rsl).isEqualTo(car);
    }

    @Test
    public void whenUpdateThenNew() {
        Car car = new Car();
        car.setName("name");
        carRepository.save(car);
        car.setName("newName");
        carRepository.update(car);
        var rsl = carRepository.findById(car.getId()).get();
        assertThat(rsl.getName()).isEqualTo("newName");
    }

    @Test
    public void whenDeleteThenEmpty() {
        Car car = new Car();
        carRepository.save(car);
        carRepository.delete(car.getId());
        var rsl = carRepository.findById(car.getId());
        assertThat(rsl).isEqualTo(Optional.empty());
    }

    @Test
    public void whenFindAllThenList() {
        Car carFirst = new Car();
        Car carSecond = new Car();
        carRepository.save(carFirst);
        carRepository.save(carSecond);
        var rsl = carRepository.findAll();
        assertThat(rsl).isEqualTo(List.of(carFirst, carSecond));
    }
}
