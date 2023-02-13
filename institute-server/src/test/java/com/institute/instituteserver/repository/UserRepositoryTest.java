package com.institute.instituteserver.repository;


import com.institute.instituteserver.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private final User testUser = User.builder()
            .firstName("Test")
            .middleName("Test")
            .lastName("Test")
            .password("Test")
            .email("test@mail.ru")
            .build();
    @Test
    public void saveUserTest() {
        userRepository.save(testUser);
        assertTrue(userRepository.exists(Example.of(testUser)));
    }

    @Test
    public void updateUserTest() {
        testUser.setFirstName("Test_2");
        testUser.setMiddleName("Test_2");
        testUser.setLastName("Test_2");
        testUser.setEmail("test_2@mail.ru");
        userRepository.save(testUser);

        Optional<User> checkIfUpdatedUser = userRepository.findByEmail("test@mail.ru");
        assertTrue(checkIfUpdatedUser.isEmpty());

        Optional<User> loadedUser = userRepository.findByEmail("test_2@mail.ru");
        assertFalse(loadedUser.isEmpty());

        assertEquals(
                loadedUser.get().getFirstName(), "Test_2"
        );
        assertEquals(
                loadedUser.get().getMiddleName(), "Test_2"
        );
        assertEquals(
                loadedUser.get().getLastName(), "Test_2"
        );
        assertEquals(
                loadedUser.get().getEmail(), "test_2@mail.ru"
        );
    }
}