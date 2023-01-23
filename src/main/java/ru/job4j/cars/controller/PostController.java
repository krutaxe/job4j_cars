package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.CarService;
import ru.job4j.cars.service.EngineService;
import ru.job4j.cars.service.PostService;
import ru.job4j.cars.util.SessionHttp;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final CarService carService;
    private final EngineService engineService;

    @GetMapping("/showInDay")
    public String showInDay(Model model, HttpSession session) {
        SessionHttp.getSessionUser(model, session);
        model.addAttribute("posts", postService.showInDay());
        return "post/posts";
    }

    @GetMapping("/withPhoto")
    public String withPhoto(Model model, HttpSession session) {
        SessionHttp.getSessionUser(model, session);
        model.addAttribute("posts", postService.showWithPhoto());
        return "post/posts";
    }

    @GetMapping("/posts")
    public String index(Model model, HttpSession session) {
        SessionHttp.getSessionUser(model, session);
        model.addAttribute("posts", postService.findAll());
        return "post/posts";
    }

    @GetMapping("/formCreatePost")
    public String createPost(Model model, HttpSession session) {
        SessionHttp.getSessionUser(model, session);
        model.addAttribute("cars", carService.findAll());
        model.addAttribute("engines", engineService.findAll());
        return "post/formCreatePost";
    }

    @PostMapping("/createPost")
    public String addPost(@ModelAttribute Post post,
                          @RequestParam("engines") int engineId,
                          @RequestParam("cars") int carId,
                          @RequestParam("file") MultipartFile file,
                          HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        Engine engine = engineService.findById(engineId);
        Car car = carService.findById(carId);
        car.setEngine(engine);
        post.setUser(user);
        post.setCar(car);
        post.setPhoto(file.getBytes());
        if (post.getPhoto().length == 0) {
            post.setPhoto(null);
        }
        postService.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/post/{id}")
    public String showPost(Model model, @PathVariable("id") int id,
                           HttpSession session) {
        SessionHttp.getSessionUser(model, session);
        Optional<Post> post = postService.findById(id);
        if (post.isEmpty()) {
            return "post/failShow";
        }
        model.addAttribute("post", post.get());
        return "post/showPost";
    }

    @PostMapping("/post/{id}")
    public String sale(@PathVariable("id") int id) {
        postService.sale(id);
        return "redirect:/post/{id}";
    }

    @GetMapping("/photoPost/{postId}")
    public ResponseEntity<Resource> download(@PathVariable("postId") Integer postId) {
        Optional<Post> post = postService.findById(postId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(post.get().getPhoto().length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(post.get().getPhoto()));
    }
}
