package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.OwnerRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    public Optional<Owner> findById(int id) {
        return ownerRepository.findById(id);
    }

    public Optional<Owner> findByUser(User user) {
        return ownerRepository.findByUser(user);
    }
}
