package com.yudis.spring.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yudis.spring.inventory.model.Role;
import com.yudis.spring.inventory.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	
	public List<Role> findAllRole() {
		return roleRepository.findAll();
	}
}
