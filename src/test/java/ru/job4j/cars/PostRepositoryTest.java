package ru.job4j.cars;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.HbmTestConfiguration;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.CarRepository;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.FileRepository;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostRepositoryTest {
    private final SessionFactory sf = HbmTestConfiguration.getSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final PostRepository postRepository = new PostRepository(crudRepository);
    private final FileRepository fileRepository = new FileRepository(crudRepository);
    private final CarRepository carRepository = new CarRepository(crudRepository);

    @Test
    public void whenSaveThenSame() {
        Post post = new Post();
        File file = new File();
        fileRepository.save(file);
        post.setFiles(List.of(file));
        postRepository.save(post);
        var rsl = postRepository.findById(post.getId()).get();
        assertThat(rsl).isEqualTo(post);
    }

    @Test
    public void whenFindFromLastDay() {
        Post postFirst = new Post();
        Post postSecond = new Post();
        File firstFile = new File();
        File secondFile = new File();
        fileRepository.save(firstFile);
        fileRepository.save(secondFile);
        postFirst.setFiles(List.of(firstFile));
        postSecond.setFiles(List.of(secondFile));
        postRepository.save(postFirst);
        postRepository.save(postSecond);
        var rsl = postRepository.findFromLastDay();
        assertThat(rsl).isEqualTo(List.of(postFirst, postSecond));
    }

    @Test
    public void whenFindWithPhoto() {
        Post postFirst = new Post();
        Post postSecond = new Post();
        File photo = new File();
        fileRepository.save(photo);
        postSecond.setFiles(List.of(photo));
        postRepository.save(postFirst);
        postRepository.save(postSecond);
        var rsl = postRepository.findWithPhoto();
        assertThat(rsl).isEqualTo(List.of(postSecond));
    }

    @Test
    public void whenFindByCarBrandThenSame() {
        Post postFirst = new Post();
        Post postSecond = new Post();
        Car carFirst = new Car();
        Car carSecond = new Car();
        carFirst.setName("nameFirst");
        carSecond.setName("nameSecond");
        carRepository.save(carFirst);
        carRepository.save(carSecond);
        postFirst.setCar(carFirst);
        postSecond.setCar(carSecond);
        File firstFile = new File();
        File secondFile = new File();
        fileRepository.save(firstFile);
        fileRepository.save(secondFile);
        postFirst.setFiles(List.of(firstFile));
        postSecond.setFiles(List.of(secondFile));
        postRepository.save(postFirst);
        postRepository.save(postSecond);
        var rsl = postRepository.findByCarBrand("nameSecond");
        assertThat(rsl).isEqualTo(List.of(postSecond));
    }
}
