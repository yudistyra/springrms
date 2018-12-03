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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyBoolean;

import java.util.Optional;

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
		Mockito.when(mockUserRepository.findByUsername(anyString())).thenReturn(null);
		
		Mockito.when(mockBCryptPasswordEncoder.encode(anyString())).thenReturn("666");
		
		Mockito.when(mockUserRepository.save(any())).thenReturn(user);
		
		final String result = userServiceUnderTest.create(user);
		
		assertEquals("success", result);
		assertNotEquals("123456", user.getPassword());
		
		Mockito.verify(user).setPassword(anyString());
		Mockito.verify(user).setActive(anyBoolean());;
	}
	
	@Test
	public void registerTest_error() {		
		Mockito.when(mockUserRepository.findByUsername(anyString())).thenReturn(user);		
		
		final String result = userServiceUnderTest.create(user);
		
		assertNotEquals("success", result);
		assertEquals("123456", user.getPassword());		
	}
	
	@Test
	public void updateTest_basic() {
		Optional<User> opt = Optional.of(user);
		
		Mockito.when(mockUserRepository.findById(anyLong())).thenReturn(opt);
		
		Mockito.when(mockUserRepository.save(any())).thenReturn(user);
		
		final String result = userServiceUnderTest.update(user);
		
		assertEquals("success", result);
		assertEquals(1, user.isActive());
		assertNull(user.getRole());
	}
}
