package com.example.demo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Auth;

@Repository
public interface AuthRepo extends JpaRepository<Auth, Integer>  {
	
//	to get data matched to username and password
	@Query("SELECT a FROM Auth a WHERE a.username = :username AND a.password = :password")
	public Auth authenticateUser(@Param("username") String username, @Param("password") String password);
	
}
