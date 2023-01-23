package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.UserRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> add(User user) {
        return userRepository.create(user);
    }

    public Optional<User> checkUser(String login, String pwd) {
        return userRepository.checkUser(login, pwd);
    }
}
