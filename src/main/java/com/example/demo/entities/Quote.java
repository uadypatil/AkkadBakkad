package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Quote {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy
	private int id;
	
	@Column(name="setup")
	private String quote;
	
	@Column(name="delivery")
	private String response;
	
	private String type;
	private String category;
	private boolean safe;
	public Quote(int id, String quote, String response, String type, String category, boolean safe) {
		super();
		this.id = id;
		this.quote = quote;
		this.response = response;
		this.type = type;
		this.category = category;
		this.safe = safe;
	}
	public Quote() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean isSafe() {
		return safe;
	}
	public void setSafe(boolean safe) {
		this.safe = safe;
	}
	
	@Override
	public String toString() {
		return "Quote [id=" + id + ", quote=" + quote + ", response=" + response + ", type=" + type + ", category="
				+ category + ", safe=" + safe + "]";
	}
	
	
	

}
