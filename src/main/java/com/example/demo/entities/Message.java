package com.example.demo.entities;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="messages")
public class Message {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "sender_user_id", unique = false) 
	private Auth senderUser;
	
	@ManyToOne
	@JoinColumn(name = "receiver_user_id", unique = false) 
	private Auth receiverUser;
	private String message;
	private Timestamp  record_timestamp;
	public Message() {
		super();
	}
	public Message(Auth senderUser, Auth receiverUser, String message, Timestamp record_timestamp) {
		super();
		this.senderUser = senderUser;
		this.receiverUser = receiverUser;
		this.message = message;
		this.record_timestamp = record_timestamp;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Auth getsenderUser() {
		return senderUser;
	}
	public void setsenderUser(Auth senderUser) {
		this.senderUser = senderUser;
	}
	public Auth getreceiverUser() {
		return receiverUser;
	}
	public void setreceiverUser(Auth receiverUser) {
		this.receiverUser = receiverUser;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Timestamp getRecord_timestamp() {
		return record_timestamp;
	}
	public void setRecord_timestamp(Timestamp record_timestamp) {
		this.record_timestamp = record_timestamp;
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", senderUser=" + senderUser + ", receiverUser=" + receiverUser + ", message="
				+ message + ", record_timestamp=" + record_timestamp + "]";
	}

	
}
