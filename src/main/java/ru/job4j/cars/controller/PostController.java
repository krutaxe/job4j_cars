package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.service.CarService;
import ru.job4j.cars.service.EngineService;
import ru.job4j.cars.service.PostService;

@Controller
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final CarService carService;
    private final EngineService engineService;

    @GetMapping("/posts")
    public String index(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "post/posts";
    }

    @GetMapping("/formCreatePost")
    public String createPost(Model model) {
        model.addAttribute("cars", carService.findAll());
        model.addAttribute("engines", engineService.findAll());
        return "post/formCreatePost";
    }

    @PostMapping("/createPost")
    public String addPost(@ModelAttribute Post post,
                          @RequestParam("engines") int engineId,
                          @RequestParam("cars") int carId) {
        Engine engine = engineService.findById(engineId);
        Car car = carService.findById(carId);
        car.setEngine(engine);
        carService.save(car);
        post.setCar(car);
        postService.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/post/{id}")
    public String showPost(Model model, @PathVariable("id") int id) {
        model.addAttribute("post", postService.findById(id).get());
        return "post/showPost";
    }
}
