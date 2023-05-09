package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Transmission;
import ru.job4j.cars.repository.TransmissionRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransmissionService {
    private TransmissionRepository transmissionRepository;

    public Optional<Transmission> findById(int id) {
        return transmissionRepository.findById(id);
    }

    public List<Transmission> findAll() {
        return transmissionRepository.findAll();
    }
}
