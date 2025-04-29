package com.example.demo;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Password {
	 public static String encrypt(String password) throws NoSuchAlgorithmException {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] hashedBytes = md.digest(password.getBytes());
	        return Base64.getEncoder().encodeToString(hashedBytes);
	    }

	    public static boolean verify(String originalPassword, String storedHash) throws NoSuchAlgorithmException {
	        // Hash the original password and compare it to the stored hash
	        String hashedPassword = encrypt(originalPassword);
	        return hashedPassword.equals(storedHash);
	    }
}
