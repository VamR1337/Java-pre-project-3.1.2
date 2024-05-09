package ru.kata.spring.boot_security.demo.servis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Transactional
    @Override
    public User addRoleToUser(User user, List<Role> roles) {
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void saveRole(List<Role> roles) {
        for (Role role : roles) {
            if(!roleRepository.existsByName(role.getName())) {
                roleRepository.save(role);
            }
        }
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getById(id);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    @Transactional
    @Override
    public void update(User user) {
        User existingUser = userRepository.getById(user.getId());
        existingUser.setUsername(user.getUsername());
        userRepository.save(existingUser);
    }
}
