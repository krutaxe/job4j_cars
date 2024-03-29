package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;
import ru.job4j.cars.util.SessionHttp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/addUser")
    public String addUser(Model model, HttpSession session) {
        SessionHttp.getSessionUser(model, session);
        return "user/addUser";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user,
                               HttpSession session) {
        SessionHttp.getSessionUser(model, session);
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message",
                    "Пользователь с таким логином уже существует");
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    @GetMapping("/fail")
    public String fail() {
        return "user/fail";
    }

    @GetMapping("/success")
    public String success() {
        return "user/success";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(
            name = "fail", required = false) Boolean fail, HttpSession session) {
        SessionHttp.getSessionUser(model, session);
        model.addAttribute("fail", fail != null);
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request) {
        Optional<User> userDb = userService.checkUser(user.getLogin(), user.getPassword());
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/posts";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
