package com.yudis.spring.inventory.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.yudis.spring.inventory.model.User;
import com.yudis.spring.inventory.repository.RoleRepository;
import com.yudis.spring.inventory.repository.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository mockUserRepository;
	@Mock
	private RoleRepository mockRoleRepository;
	@Mock
	private BCryptPasswordEncoder mockBCryptPasswordEncoder;
	
	@InjectMocks
	private UserService userServiceUnderTest;
	
	private User user;
	
	@Before
	public void setUp() throws Exception {
		 user = User.builder()
				 .id(1L)
				 .username("usertest")
				 .name("User Test")
				 .email("test@test.com")
				 .build();

	        Mockito.when(mockUserRepository.save(any()))
	                .thenReturn(user);
	        Mockito.when(mockUserRepository.findByUsername(anyString()))
	                .thenReturn(user);
	}

	@Test
	public void findUserByUsernameTest() {
		final String username = "usertest";
		
		final User result = userServiceUnderTest.findUserByUsername(username);
		
		assertEquals(username, result.getUsername());
	}
	
	@Test
	public void saveUserTest() {
		final String username = "usertest";
		
		final User result = userServiceUnderTest.saveUser(User.builder().build());
		
		assertEquals(username, result.getUsername());
	}

}
