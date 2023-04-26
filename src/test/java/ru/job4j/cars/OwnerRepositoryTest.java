package ru.job4j.cars;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HbmTestConfiguration;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.OwnerRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class OwnerRepositoryTest {
    private final SessionFactory sf = HbmTestConfiguration.getSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final OwnerRepository ownerRepository = new OwnerRepository(crudRepository);

    @Test
    public void whenSaveThenSame() {
        Owner owner = new Owner();
        ownerRepository.save(owner);
        var rsl = ownerRepository.findById(owner.getId()).get();
        assertThat(rsl).isEqualTo(owner);
    }

    @Test
    public void whenUpdateThenNew() {
        Owner owner = new Owner();
        owner.setName("name");
        ownerRepository.save(owner);
        owner.setName("newName");
        ownerRepository.update(owner);
        var rsl = ownerRepository.findById(owner.getId()).get();
        assertThat(rsl.getName()).isEqualTo("newName");
    }

    @Test
    public void whenDeleteThenEmpty() {
        Owner owner = new Owner();
        ownerRepository.save(owner);
        ownerRepository.delete(owner.getId());
        var rsl = ownerRepository.findById(owner.getId());
        assertThat(rsl).isEqualTo(Optional.empty());
    }

    @Test
    public void whenFindAllThenList() {
        Owner ownerFirst = new Owner();
        Owner ownerSecond = new Owner();
        ownerRepository.save(ownerFirst);
        ownerRepository.save(ownerSecond);
        var rsl = ownerRepository.findAll();
        assertThat(rsl).isEqualTo(List.of(ownerFirst, ownerSecond));
    }
}
