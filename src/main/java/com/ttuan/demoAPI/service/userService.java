package com.ttuan.demoAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttuan.demoAPI.Repository.userRepository;
import com.ttuan.demoAPI.model.user;


@Service
public class userService {
	
	@Autowired
	private userRepository repo;
	
	public List<user> listAll(){
		return repo.findAll();
	}
	
	 public void save(user t) {
	        repo.save(t);
    }
	     
    public user get(Integer id) {
        return repo.findById(id).get();
    }
     
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
