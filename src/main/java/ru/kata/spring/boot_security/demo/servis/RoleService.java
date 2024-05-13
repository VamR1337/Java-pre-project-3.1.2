package ru.kata.spring.boot_security.demo.servis;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    void saveRole(List<Role> roles);
    List<Role> findAll();
    Role findByName(String role);
}
