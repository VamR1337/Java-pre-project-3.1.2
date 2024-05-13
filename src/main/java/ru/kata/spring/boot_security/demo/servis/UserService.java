package ru.kata.spring.boot_security.demo.servis;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {
    void add(User user);
    User addRoleToUser(User user, List<Role> roles);
    void delete(long id);
    List<User> listUsers();
    User getUserById(long id);
    User getUserByName(String name);
    void update(User user);
}
