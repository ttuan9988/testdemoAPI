package com.ttuan.demoAPI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ttuan.demoAPI.model.user;


public interface userRepository extends JpaRepository<user, Integer>{
	@Query("SELECT u FROM user u WHERE u.username= ?1")
	user  findByUsername(String username);
	
}
