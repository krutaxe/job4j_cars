package ru.job4j.cars.util;

import lombok.NoArgsConstructor;
import org.springframework.ui.Model;
import ru.job4j.cars.model.User;

import javax.servlet.http.HttpSession;

@NoArgsConstructor
public class SessionHttp {
    public static void getSessionUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setLogin("Гость");
        }
        model.addAttribute("userSession", user);
    }
}
