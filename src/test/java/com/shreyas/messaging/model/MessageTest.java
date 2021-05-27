package com.shreyas.messaging.model;

import com.shreyas.messaging.MessagingApplication;
import com.shreyas.messaging.repository.MessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MessagingApplication.class)
@WebAppConfiguration
public class MessageTest {

    @Autowired
    private MessageRepository messageRepository;

    private Message message1;
    private Message message2;

    @Before
    public void setUp() {
        message1 = new Message(1L, 123L, 345L, "Hey wassup ma nigga");
        message2 = new Message(2L, 567L, 789L, "Hey nigga");

        messageRepository.deleteAll();
        messageRepository.saveAll(Arrays.asList(message1, message2));
    }

    @Test
    public void testFetchMessage() {
        String str1 = "Hey wassup ma nigga";
        String str2 = "Hey nigga";

        assertThat(message1.getMessage()).isEqualTo(str1);
        assertThat(message2.getMessage()).isEqualTo(str2);
    }

    @Test
    public void testFetchSenderID() {
        assertThat(message1.getSenderId()).isEqualTo(123L);
        assertThat(message2.getSenderId()).isEqualTo(567L);
    }
}
