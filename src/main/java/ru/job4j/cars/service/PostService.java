package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Category;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post saveWithFiles(Post post, List<File> savedFiles) {
        post.setFiles(savedFiles);
        return postRepository.save(post);
    }

    public boolean delete(int id) {
        return postRepository.delete(id);
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findFromLastDay() {
        return postRepository.findFromLastDay();
    }

    public List<Post> findWithPhoto() {
        return postRepository.findWithPhoto();
    }

    public List<Post> findByCarBrand(String brand) {
        return postRepository.findByCarBrand(brand);
    }

    public List<Post> findByCategory(Category category) {
        return postRepository.findByCategory(category);
    }

    public List<Post> findByState(boolean state) {
        return postRepository.findByState(state);
    }

    public List<Post> findBySold(boolean carSold) {
        return postRepository.findBySold(carSold);
    }

    public boolean updateStates(int id) {
        return postRepository.updateStates(id);
    }
}
