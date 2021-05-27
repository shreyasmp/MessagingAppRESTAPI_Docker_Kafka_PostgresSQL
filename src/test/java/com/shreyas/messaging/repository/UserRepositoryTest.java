package com.shreyas.messaging.repository;

import com.shreyas.messaging.MessagingApplication;
import com.shreyas.messaging.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = MessagingApplication.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void testUserByUserName() {
        User expected = new User(1234L, "homie123");
        repository.save(expected);
        Optional<User> actual = repository.findByName("homie123");
        assertEquals(expected.getName(), actual.get().getName());
    }
}