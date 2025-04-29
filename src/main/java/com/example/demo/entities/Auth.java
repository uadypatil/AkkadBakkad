package com.example.demo.entities;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Access(AccessType.FIELD) // or AccessType.PROPERTY if required
@Table(name="auth")
public class Auth {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy
	private int id;
	private String username;
	private String password;
	private String role;
	private int status = 1;
	public Auth() {
		super();

	}
	
	
	
	public Auth(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Map get(String role) {
		HashMap<String, Object> authUser = new HashMap<String, Object>();
		authUser.put("id", Integer.valueOf(this.id));
		authUser.put(username, this.username);
		authUser.put("role", this.role);
		authUser.put("status", Integer.valueOf(this.status));
		
		
		return authUser;
	}



	@Override
	public String toString() {
		return "Auth [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", status="
				+ status + "]";
	}
	

}
