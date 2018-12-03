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
	
	public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
	
	public User findUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
    
    public String create(User user) {
    	User userExist = findUserByUsername(user.getUsername());
    	
    	if(userExist != null) {
    		return "There is already a user registered with the username provided";
    	}
    	else {
    		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(false);
    		User newuser = userRepository.save(user);
    		if(newuser != null) {
    			return "success";
    		}
    	}
    	
    	return "failed";
    }
        
    public List<User> findAllUserByActive(boolean active) {
    	return userRepository.findAllByActive(active);
    }
    
    public List<User> findAllUser() {
    	return userRepository.findAll();
    }

	public String update(User user) {
		User userUpdate = userRepository.findById(user.getId()).orElse(null);
		
		if(userUpdate != null) {
			userUpdate.setActive(user.isActive());
			userUpdate.setRole(user.getRole());
			
			User resultUser = userRepository.save(userUpdate);
			
			if(resultUser != null) return "success";
		}
			
		return "failed";
	}
}
