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
import com.yudis.spring.inventory.repository.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.atLeastOnce;
import static org.hamcrest.Matchers.contains;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository mockUserRepository;
	
	@Mock
	private BCryptPasswordEncoder mockBCryptPasswordEncoder;
	
	@InjectMocks
	private UserService userServiceUnderTest;
	
	private User user;
	
	@Before
	public void setUp() throws Exception {
		 user = Mockito.spy(User.builder()
				 .id(1L)
				 .username("usertest")
				 .name("User Test")
				 .email("test@test.com")
				 .password("123456")
				 .build());
	}

	@Test
	public void registerTest_basic() {
		//User testUser = User.builder().build();
		
		Mockito.when(mockUserRepository.findByUsername(anyString())).thenReturn(null);
		
		Mockito.when(mockBCryptPasswordEncoder.encode(anyString())).thenReturn("666");
		
		Mockito.when(mockUserRepository.save(any())).thenReturn(user);
		
		final String result = userServiceUnderTest.create(user);
		
		assertEquals("success", result);
		assertNotEquals("123456", user.getPassword());
		
		Mockito.verify(user).setPassword(anyString());
		Mockito.verify(user).setActive(anyInt());;
	}
	
	@Test
	public void registerTest_error() {		
		Mockito.when(mockUserRepository.findByUsername(anyString())).thenReturn(user);		
		
		final String result = userServiceUnderTest.create(user);
		
		assertNotEquals("success", result);
		assertEquals("123456", user.getPassword());		
	}
	
	@Test
	public void findUserByActiveTest_basic() {
		Mockito.when(mockUserRepository.findAllByActive(anyInt())).thenReturn(Arrays.asList(user,user));
		
		final List<User> activeUsers = userServiceUnderTest.findAllUserByActive(1);
		
		assertNotNull(activeUsers);
		assertEquals(2, activeUsers.size());
		
		Mockito.verify(mockUserRepository, atLeastOnce()).findAllByActive(1);
	}
	
	@Test
	public void findUserByActiveTest_error() {
		final int active = 2; 
		final List<User> activeUsers = userServiceUnderTest.findAllUserByActive(active);
		
		assertNull(activeUsers);
		assertNotEquals(active, contains(0,1));
		
		Mockito.verify(mockUserRepository, never()).findAllByActive(active);
	}
}
