package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cars.service.PostService;

@Controller
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public String index(Model model) {
        model.addAttribute("posts", postService.findAll());
        System.out.println("Hello");
        return "post/posts";
    }
}
