package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

//	to get messages by sender and receiver id
	@Query("SELECT m FROM Message m WHERE (m.senderUser.id = :senderUserId AND m.receiverUser.id = :receiverUserId) OR ( m.senderUser.id = :receiverUserId AND m.receiverUser.id = :senderUserId) ORDER BY m.record_timestamp ASC")
	public List<Message> getMessagesBySenderReceiver(@Param("senderUserId") int senderUserId,
	                                                 @Param("receiverUserId") int receiverUserId);




}
