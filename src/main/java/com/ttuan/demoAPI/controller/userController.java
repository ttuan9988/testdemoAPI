package com.ttuan.demoAPI.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ttuan.demoAPI.jwt.JwtTokenUtil;
import com.ttuan.demoAPI.model.authRequest;
import com.ttuan.demoAPI.model.authResponse;
import com.ttuan.demoAPI.model.user;
import com.ttuan.demoAPI.service.userService;

@RestController
public class userController {

	@Autowired
	private userService service;
	
	
	
	@GetMapping("/user")
	public List<user> list(){
		return service.listAll();
	}
	@PostMapping("/user/add")
	public void add(@RequestBody user user) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String pEncoder = passwordEncoder.encode(user.getPassword());
		user.setPassword(pEncoder);
		service.save(user);
		
		
	}
	
	@Autowired AuthenticationManager authManager;
	@Autowired JwtTokenUtil jwtUtil;
	@PostMapping("/user/login")
	public ResponseEntity<?> login(@RequestBody @Valid authRequest request){
		try {
			
			Authentication authentication =  authManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
					
					);
			user user= (user) authentication.getPrincipal();
			
			String accessToken= jwtUtil.createAccessToken(user);
			authResponse response = new authResponse(user.getUsername(), accessToken);
			
			return ResponseEntity.ok(response);
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
