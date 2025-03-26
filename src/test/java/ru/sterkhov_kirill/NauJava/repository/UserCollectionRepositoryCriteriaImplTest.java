package ru.sterkhov_kirill.NauJava.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sterkhov_kirill.NauJava.entity.UserCollectionEntity;
import ru.sterkhov_kirill.NauJava.entity.UserEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserCollectionRepositoryCriteriaImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCollectionRepository userCollectionRepository;

    @Autowired
    private UserCollectionRepositoryCriteriaImpl userCollectionRepositoryCriteria;

    @Test
    void findByUsername_ShouldUseCriteria() {
        String username = "user_test_criteria";
        UserEntity user = createTestUser(username);
        userRepository.save(user);

        UserCollectionEntity collection = new UserCollectionEntity();
        collection.setUser(user);
        collection.setName("collection_test_criteria");
        userCollectionRepository.save(collection);

        List<UserCollectionEntity> result = userCollectionRepositoryCriteria.findByUsername(username);

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
