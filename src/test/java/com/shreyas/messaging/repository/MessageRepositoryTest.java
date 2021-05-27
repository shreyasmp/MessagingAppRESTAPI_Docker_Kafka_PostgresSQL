package com.shreyas.messaging.repository;

import com.shreyas.messaging.MessagingApplication;
import com.shreyas.messaging.model.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = MessagingApplication.class)
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void testFetchAllMessagesBySender() {
        Message message = new Message(1L, 111L, 222L, "Ma homie!!");
        Message message1 = new Message(2L, 112L, 223L, "Yo ma homie");
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(message1);
        messageRepository.saveAll(messages);
        assertNotNull(messageRepository.findAllBySenderId(111L));
    }

    @Test
    public void testFetchAllMessagesByReceiver() {
        Message message = new Message(1L, 111L, 222L, "Ma homie!!");
        Message message1 = new Message(2L, 112L, 223L, "Yo ma homie");
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(message1);
        messageRepository.saveAll(messages);
        assertNotNull(messageRepository.findAllByReceiverId(223L));
    }

    @Test
    public void testFetchAllMessagesBySenderAndReceiver() {
        Message message = new Message(1L, 111L, 222L, "Ma homie!!");
        Message message1 = new Message(2L, 112L, 223L, "Yo ma homie");
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(message1);
        messageRepository.saveAll(messages);
        assertNotNull(messageRepository.findAllBySenderIdAndReceiverId(111L, 222L));
    }
}