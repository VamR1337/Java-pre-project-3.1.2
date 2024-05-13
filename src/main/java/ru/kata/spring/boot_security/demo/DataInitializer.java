package ru.kata.spring.boot_security.demo;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.servis.RoleService;
import ru.kata.spring.boot_security.demo.servis.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Log4j2
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.saveRole(List.of(new Role("USER"), new Role("ADMIN")));

        User user1 = new User("user", "user");
        User user2 = new User("admin", "admin");

        userService.add(user1);
        userService.add(user2);

        Role userRole = roleService.findByName("USER");
        Role adminRole = roleService.findByName("ADMIN");

        userService.addRoleToUser(user1, List.of(userRole));
        userService.addRoleToUser(user2, List.of(adminRole, userRole));
    }
}