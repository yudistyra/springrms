package com.yudis.spring.inventory.service;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yudis.spring.inventory.model.User;
import com.yudis.spring.inventory.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	private User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        return userRepository.save(user);
    }
	
	public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }    
    
    public String register(User user) {
    	User userExist = findUserByUsername(user.getUsername());
    	
    	if(userExist != null) {
    		return "There is already a user registered with the username provided";
    	}
    	else {
    		User newuser = saveUser(user);
    		if(newuser != null) {
    			return "success";
    		}
    	}
    	
    	return "failed";
    }
    
    public List<User> findAllUserByActive(int active) {
    	return userRepository.findAllByActive(active);
    }
    
    public List<User> findAllUser() {
    	return userRepository.findAll();
    }
}
