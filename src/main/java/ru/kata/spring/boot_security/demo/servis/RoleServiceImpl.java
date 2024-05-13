package ru.kata.spring.boot_security.demo.servis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    };

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Role findByName(String role) {
        return roleRepository.findByName(role).orElseThrow();
    };
}
