package ru.sterkhov_kirill.NauJava.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sterkhov_kirill.NauJava.entity.UserCollectionEntity;
import ru.sterkhov_kirill.NauJava.entity.UserEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserCollectionRepositoryTest {

    @Autowired
    private UserCollectionRepository userCollectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername_ShouldReturnCollections() {
        String username = "user_test";
        UserEntity user = createTestUser(username);
        userRepository.save(user);

        UserCollectionEntity collection = new UserCollectionEntity();
        collection.setUser(user);
        collection.setName("collection_test");
        userCollectionRepository.save(collection);

        List<UserCollectionEntity> result = userCollectionRepository.findByUsername(username);

        assertEquals(1, result.size());
        assertEquals(username, result.getFirst().getUser().getUsername());
    }

    private UserEntity createTestUser(String username) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPasswordHash("kdjflsefsd121esf");
        user.setEmail(username + "@example.com");
        return user;
    }
}
