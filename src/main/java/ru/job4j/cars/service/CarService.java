package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public boolean update(Car car) {
        return carRepository.update(car);
    }

    public boolean delete(int id) {
        return carRepository.delete(id);
    }

    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }
}
