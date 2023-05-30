package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private CarService carService;
    private final CategoryService categoryService;
    private final BodyService bodyService;
    private final EngineService engineService;
    private final TransmissionService transmissionService;
    private final OwnerService ownerService;
    private final FileService fileService;

    @GetMapping({"/", "/index"})
    public String getAll(Model model) {
        List<Category> categories = categoryService.findAll();
        List<Post> posts = postService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("/category")
    public String getCategory(@RequestParam(name = "category", required = false) Integer categoryId, Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
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
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        List<Post> posts = postService.findByState(state);
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("/filter")
    public String getByFilter(@RequestParam(name = "filter", required = false) String filter, Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        switch (filter) {
            case "lastDay" -> model.addAttribute("posts", postService.findFromLastDay());
            case "withPhoto" -> model.addAttribute("posts", postService.findWithPhoto());
            case "isSold" -> model.addAttribute("posts", postService.findBySold(false));
            default -> model.addAttribute("posts", postService.findAll());
        }
        return "posts/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
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
    public String create(@ModelAttribute PostDto postDto, @SessionAttribute User user,
                         @RequestParam("categoryId") int categoryId,
                         @RequestPart("files") List<MultipartFile> files, Model model) {
        try {
            Owner owner = ownerService.findByUser(user).orElseGet(() -> {
                Owner newOwner = new Owner();
                newOwner.setUser(user);
                newOwner.setName(user.getName());
                newOwner.setPhone(postDto.getPhone());
                ownerService.save(newOwner);
                return newOwner;
            });
            Car car = new Car();
            car.setName(postDto.getCarName());
            car.setModel(postDto.getCarModel());
            car.setCategory(categoryService.findById(categoryId).get());
            car.setBody(bodyService.findById(postDto.getBodyId()).get());
            car.setEngine(engineService.findById(postDto.getEngineId()).get());
            car.setTransmission(transmissionService.findById(postDto.getTransmissionId()).get());
            car.setOwner(owner);
            car.getOwners().add(owner);
            carService.save(car);
            Post post = new Post();
            post.setDescription(postDto.getDescription());
            post.setCar(car);
            post.setProductionYear(postDto.getProductionYear());
            post.setMileage(postDto.getMileage());
            post.setCarNew(postDto.isCarNew());
            post.setPrice(postDto.getPrice());
            post.setUser(user);
            List<File> savedFiles = fileService.convertMultipartInFile(files);
            postService.saveWithFiles(post, savedFiles);
            model.addAttribute("message", "Ваше объявление добавлено успешно!");
            return "success/success";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/one/{id}")
    public String getById(Model model, @PathVariable int id) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Ошибка. Объявление не найдено");
            return "errors/404";
        }
        model.addAttribute("post", postOptional.get());
        return "posts/one";
    }

    @GetMapping("/state/{id}")
    public String updateState(Model model, @PathVariable int id, @SessionAttribute User user) {
        model.addAttribute("user", user);
        var isUpdatedState = postService.updateStates(id);
        if (!isUpdatedState) {
            model.addAttribute("message", "Ошибка. Статус объявления не обновлен");
            return "errors/404";
        }
        model.addAttribute("message", "Статус объявления изменен на Продано");
        return "success/success";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id, @SessionAttribute User user) {
        model.addAttribute("user", user);
        var isDeleted = postService.delete(id);
        if (!isDeleted) {
            model.addAttribute("message", "Ошибка. Объявление не удалено");
            return "errors/404";
        }
        model.addAttribute("message", "Объявление удалено успешно");
        return "success/success";
    }
}
