package com.yudis.spring.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yudis.spring.inventory.model.Role;
import com.yudis.spring.inventory.model.User;
import com.yudis.spring.inventory.repository.RoleRepository;
import com.yudis.spring.inventory.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByName("ADMIN");
        user.setRole(userRole);
        return userRepository.save(user);
    }
}
