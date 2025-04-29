package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.demo")
public class SpringUi1Application {
	
	public SpringUi1Application() {
		super();
		System.out.println("---------------------------------------------------------------------------------------------------------");
		System.out.println("Program Started...");
		System.out.println("---------------------------------------------------------------------------------------------------------");
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringUi1Application.class, args);
	}

}
