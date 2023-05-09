package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final FileService fileService;
    private final CategoryService categoryService;
    private final BodyService bodyService;
    private final EngineService engineService;
    private final TransmissionService transmissionService;

    @GetMapping({"/", "/index"})
    public String getAll(Model model) {
        List<Category> categories = categoryService.findAll();
        List<Post> posts = postService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("/category")
    public String getCategory(@RequestParam(name = "id", required = false) Integer categoryId, Model model) {
        if (categoryId != null) {
            var categoryOptional = categoryService.findById(categoryId);
            if (categoryOptional.isPresent()) {
                List<Post> posts = postService.findByCategory(categoryOptional.get());
                model.addAttribute("posts", posts);
            }
        }
        return "posts/list";
    }

    @GetMapping("/state")
    public String getPostsByState(@RequestParam(name = "state") boolean state, Model model) {
        List<Post> posts = postService.findByState(state);
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("/filter")
    public String getByFilter(@RequestParam(name = "filter", required = false) String filter, Model model) {
        switch (filter) {
            case "lastDay" -> model.addAttribute("posts", postService.findFromLastDay());
            case "withPhoto" -> model.addAttribute("posts", postService.findWithPhoto());
            case "isSold" -> model.addAttribute("posts", postService.findBySold(false));
            default -> model.addAttribute("posts", postService.findAll());
        }
        return "posts/list";
    }

    @GetMapping
    public String search(@RequestParam("query") String query, Model model) {
        List<Post> posts = postService.findByCarBrand(query);
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("/create_category")
    public String carCategory(@RequestParam(name = "categoryId", required = false) Integer categoryId,
                              Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        if (categoryId != null) {
            model.addAttribute("categoryId", categoryId);
            return "redirect:/posts/create?categoryId=" + categoryId;
        }
        return "posts/category";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model,
                                  @RequestParam(name = "categoryId", required = false) Integer categoryId) {
        model.addAttribute("post", new Post());
        if (categoryId != null) {
            List<Body> bodies = bodyService.findByCategoryId(categoryId);
            model.addAttribute("bodies", bodies);
            model.addAttribute("engines", engineService.findAll());
            model.addAttribute("transmissions", transmissionService.findAll());
            model.addAttribute("categoryId", categoryId);
        }
        return "posts/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Post post, @SessionAttribute User user,
                         @RequestParam("categoryId") int categoryId,
                         @RequestParam("bodyId") int bodyId,
                         @RequestParam("engineId") int engineId,
                         @RequestParam("transmissionId") int transmissionId,
                         @RequestParam("files") List<MultipartFile> files, Model model) {
        try {
            Car car = new Car();
            car.setCategory(categoryService.findById(categoryId).get());
            car.setBody(bodyService.findById(bodyId).get());
            car.setEngine(engineService.findById(engineId).get());
            car.setTransmission(transmissionService.findById(transmissionId).get());
            Owner owner = new Owner();
            owner.setUser(user);
            car.setOwner(owner);
            post.setCar(car);
            post.setUser(user);
            List<File> fileList = fileService.convertToFiles(files);
            post.setFiles(fileList);
            postService.save(post);
            model.addAttribute("message", "Ваше объявление добавлено успешно!");
            return "success/success";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Задание не найдено");
            return "errors/404";
        }
        model.addAttribute("post", postOptional.get());
        return "posts/one";
    }

    @GetMapping("/state/{id}")
    public String updateState(Model model, @PathVariable int id) {
        var isUpdatedState = postService.updateStates(id);
        if (!isUpdatedState) {
            model.addAttribute("message", "Ошибка. Статус объявления не обновлен");
            return "errors/404";
        }
        model.addAttribute("message", "Статус объявления изменен на Продано");
        return "success/success";
    }
}
