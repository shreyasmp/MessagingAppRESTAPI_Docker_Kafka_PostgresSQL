package com.shreyas.messaging.repository;

import com.shreyas.messaging.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    String FIND_ALL_BY_SENDER_ID = "SELECT m FROM messages m WHERE m.sender_id = :senderId";
    String FIND_ALL_BY_RECEIVER_ID = "SELECT m FROM messages m WHERE m.receiver_id = :receiverId";
    String FIND_ALL_BY_SENDER_ID_AND_RECEIVER_ID = "SELECT m FROM messages m WHERE m.sender_id = :senderId AND m.receiver_id = :receiverId";

    @Query(value = FIND_ALL_BY_SENDER_ID, nativeQuery = true)
    List<Message> findAllBySenderId(@Param("senderId") Long senderId);

    @Query(value = FIND_ALL_BY_RECEIVER_ID, nativeQuery = true)
    List<Message> findAllByReceiverId(@Param("receiverId") Long receiverId);

    @Query(value = FIND_ALL_BY_SENDER_ID_AND_RECEIVER_ID, nativeQuery = true)
    List<Message> findAllBySenderIdAndReceiverId(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);
}
