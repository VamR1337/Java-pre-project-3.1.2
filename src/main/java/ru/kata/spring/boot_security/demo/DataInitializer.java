package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.servis.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("user", passwordEncoder.encode("user"));
        User user2 = new User("admin", passwordEncoder.encode("admin"));

        Role user = new Role("USER");
        Role admin = new Role("ADMIN");
        userService.saveRole(List.of(user, admin));

        userService.add(userService.addRoleToUser(user1, List.of(user)));
        userService.add(userService.addRoleToUser(user2, List.of(admin,user)));
    }
}