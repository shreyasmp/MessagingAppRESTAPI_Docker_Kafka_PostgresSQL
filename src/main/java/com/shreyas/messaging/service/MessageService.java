package com.shreyas.messaging.service;

import com.shreyas.messaging.model.Message;
import com.shreyas.messaging.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessage() {
        return messageRepository.findAll();
    }

    public Message getMessage(Long messageId) {
        if (messageRepository.findById(messageId).isPresent()) {
            return messageRepository.findById(messageId).get();
        }
        return null;
    }

    public List<Message> getAllSentMessages(Long senderId) {
        return messageRepository.findAllBySenderId(senderId);
    }

    public List<Message> getAllReceivedMessage(Long receiverId) {
        return messageRepository.findAllByReceiverId(receiverId);
    }

    public List<Message> getAllMessages(Long senderId, Long receiverId) {
        return messageRepository.findAllBySenderIdAndReceiverId(senderId, receiverId);
    }
}
