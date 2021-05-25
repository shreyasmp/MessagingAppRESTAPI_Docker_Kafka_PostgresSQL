package com.shreyas.messaging.controller;

import com.shreyas.messaging.data.MessageDTO;
import com.shreyas.messaging.exception.DuplicateEntryException;
import com.shreyas.messaging.exception.EmptyOrInvalidInputException;
import com.shreyas.messaging.model.Message;
import com.shreyas.messaging.service.MessageProducer;
import com.shreyas.messaging.service.MessageService;
import com.shreyas.messaging.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messaging")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageUtil messageUtil;

    @PostMapping("/messages/send")
    public ResponseEntity<?> sendMessage(@RequestHeader(value = "user_id") Long userId, @RequestBody MessageDTO messageDTO) {
        if (messageDTO.getReceiverId() == null || messageDTO.getReceiverId().isEmpty()) {
            throw new EmptyOrInvalidInputException("601", "Invalid input! Value cannot be null or empty!");
        }
        messageDTO.setSenderId(Long.toString(userId));
        if (messageDTO.getSenderId().equals(messageDTO.getReceiverId())) {
            throw new DuplicateEntryException("603", "Operation not allowed!");
        }
        logger.info("Saving message to database....");
        Message message = messageService.saveMessage(messageUtil.dtoToModel(messageDTO));
        String kafkaResponse = messageProducer.publishMessageToKafka(message);
        return new ResponseEntity<>(kafkaResponse, HttpStatus.CREATED);
    }

    @GetMapping("/messages/sent")
    public ResponseEntity<List<MessageDTO>> getAllSentMessages(@RequestHeader(value = "user_id") Long userId) {
        List<MessageDTO> messages = messageUtil.modelToDTO(messageService.getAllSentMessages(userId));
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/messages/received")
    public ResponseEntity<List<MessageDTO>> getAllReceivedMessages(@RequestHeader(value = "user_id") Long userId) {
        List<MessageDTO> messages = messageUtil.modelToDTO(messageService.getAllReceivedMessage(userId));
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/messages/sender/{sender_id}")
    public ResponseEntity<List<MessageDTO>> getAllReceivedMessagesFromSender(@RequestHeader(value = "user_id") Long userId, @PathVariable("sender_id") Long senderId) {
        List<MessageDTO> messages = messageUtil.modelToDTO(messageService.getAllMessages(senderId, userId));
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        List<MessageDTO> messages = messageUtil.modelToDTO(messageService.getAllMessage());
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<MessageDTO> getMessage(@PathVariable("id") Long messageId) {
        MessageDTO msgDto = messageUtil.modelToDTO(messageService.getMessage(messageId));
        return new ResponseEntity<>(msgDto, HttpStatus.OK);
    }
}
