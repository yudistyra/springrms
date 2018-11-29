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

import com.yudis.spring.inventory.model.Role;
import com.yudis.spring.inventory.model.User;
import com.yudis.spring.inventory.repository.RoleRepository;
import com.yudis.spring.inventory.repository.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;

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
	private Role role;
	
	@Before
	public void setUp() throws Exception {
		 user = User.builder()
				 .id(1L)
				 .username("usertest")
				 .name("User Test")
				 .email("test@test.com")
				 .build();

		 role = Role.builder()
				 .id(1)
				 .name("USER")
				 .build();
		 
	        Mockito.when(mockUserRepository.save(any()))
	                .thenReturn(user);
	        Mockito.when(mockUserRepository.findByUsername(anyString()))
	                .thenReturn(user);
	        Mockito.when(mockUserRepository.findAll()).thenReturn(Arrays.asList(
	        		user,user,user
	        ));
	}

	@Test
	public void findUserByUsernameTest() {
		final String username = "usertest";
		
		final User result = userServiceUnderTest.findUserByUsername(username);
		
		assertEquals(username, result.getUsername());
	}
	
	@Test
	public void registerTest() {	
		final String result = userServiceUnderTest.register(User.builder().build());
		
		assertEquals("success", result);
	}
	
	@Test
	public void findAllUserActiveTest() {
		Mockito.when(mockUserRepository.findAllByActive(1)).thenReturn(Arrays.asList(
        		new User(1L,"user1","123456","user1@mail.com","user1",1,role),
                new User(2L,"user2","123456","user2@mail.com","user2",1,role),
                new User(3L,"user3","123456","user3@mail.com","user3",0,role)
        ));
		
		List<User> users = userServiceUnderTest.findAllUserByActive(1);
		
		assertEquals(3, users.size());
	}
	
	@Test(expected = NullPointerException.class)
	public void findAllUserActiveTestError() {
		Mockito.when(userServiceUnderTest.findAllUserByActive(2)).thenThrow(NullPointerException.class);
		
		List<User> users = userServiceUnderTest.findAllUserByActive(2);
	}
	
	@Test
	public void findAllUser() {
		List<User> users = userServiceUnderTest.findAllUser();
		
		assertEquals(3, users.size());
	}

}
