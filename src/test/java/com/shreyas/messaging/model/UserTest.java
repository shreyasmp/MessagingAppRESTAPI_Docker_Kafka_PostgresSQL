package com.shreyas.messaging.model;

import com.shreyas.messaging.MessagingApplication;
import com.shreyas.messaging.repository.UserRepository;
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
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @Before
    public void setUp() {
        user1 = new User(1000L, "abc123");
        user2 = new User(1001L, "xyz123");

        userRepository.deleteAll();
        userRepository.saveAll(Arrays.asList(user1, user2));
    }

    @Test
    public void testFetchUserId() {
        assertThat(user1.getId()).isEqualTo(1000L);
        assertThat(user2.getId()).isEqualTo(1001L);
    }

    @Test
    public void testFetchUserName() {
        assertThat(user1.getName()).isEqualTo("abc123");
        assertThat(user2.getName()).isEqualTo("xyz123");
    }
}