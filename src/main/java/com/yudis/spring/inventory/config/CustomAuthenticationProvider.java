package com.yudis.spring.inventory.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.yudis.spring.inventory.model.User;
import com.yudis.spring.inventory.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
	    String password = authentication.getCredentials().toString();
	    
	    User user = userService.findUserByUsername(username);
	    
	    if (user == null) {
	        throw new BadCredentialsException("Bad Credentials");
	    }
	    if (user.getActive() == 0) {
	        throw new DisabledException("Username not active");
	    }
	    if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
	        throw new BadCredentialsException("Password doesn't match");
	    }
	    
	    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        
        Authentication auth = new UsernamePasswordAuthenticationToken(user,password,grantedAuthorities);
	    //return new UsernamePasswordAuthenticationToken(user,password,grantedAuthorities);
        System.out.println(auth.getPrincipal());
        return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
