package com.ttuan.demoAPI.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ttuan.demoAPI.Repository.userRepository;
import com.ttuan.demoAPI.jwt.JwtTokenFilter;

@EnableWebSecurity
public class userSercurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired 
	private userRepository userrepo;
	
	@Autowired private JwtTokenFilter jwtTokenFilter;
	
	@Override 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(username ->userrepo.findByUsername(username));
	}
	
	
	
	@Override @Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable();
	    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	     
	    http.authorizeRequests()
	            .antMatchers("/user/login").permitAll()
	            .antMatchers("/user/add").permitAll()
	            .anyRequest().authenticated();
		http.exceptionHandling()
        .authenticationEntryPoint(
            (request, response, ex) -> {
                response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    ex.getMessage()
                );
            }
        );
 
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

	
}
