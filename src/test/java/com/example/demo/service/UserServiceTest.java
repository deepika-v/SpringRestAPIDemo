package com.example.demo.service;

import com.example.demo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void getAllUsers_shouldReturnEmptyListInitially() {
        List<User> users = userService.getAllUsers();
        assertThat(users).isEmpty();
    }

    @Test
    void createUser_shouldCreateAndReturnUser() {
        User user = new User(null, "John Doe", "john@example.com", null);
        User created = userService.createUser(user);

        assertThat(created.id()).isNotNull();
        assertThat(created.name()).isEqualTo("John Doe");
        assertThat(created.email()).isEqualTo("john@example.com");
        assertThat(created.createdAt()).isNotNull();
    }

    @Test
    void getUserById_shouldReturnUserIfExists() {
        User user = new User(null, "Jane Doe", "jane@example.com", null);
        User created = userService.createUser(user);

        Optional<User> found = userService.getUserById(created.id());
        assertThat(found).isPresent();
        assertThat(found.get().name()).isEqualTo("Jane Doe");
    }

    @Test
    void getUserById_shouldReturnEmptyIfNotExists() {
        Optional<User> found = userService.getUserById(999L);
        assertThat(found).isEmpty();
    }

    @Test
    void updateUser_shouldUpdateAndReturnUser() {
        User user = new User(null, "Old Name", "old@example.com", null);
        User created = userService.createUser(user);

        User updatedUser = new User(null, "New Name", "new@example.com", null);
        Optional<User> updated = userService.updateUser(created.id(), updatedUser);

        assertThat(updated).isPresent();
        assertThat(updated.get().name()).isEqualTo("New Name");
        assertThat(updated.get().email()).isEqualTo("new@example.com");
        assertThat(updated.get().createdAt()).isEqualTo(created.createdAt());
    }

    @Test
    void updateUser_shouldReturnEmptyIfNotExists() {
        User updatedUser = new User(null, "New Name", "new@example.com", null);
        Optional<User> updated = userService.updateUser(999L, updatedUser);
        assertThat(updated).isEmpty();
    }

    @Test
    void deleteUser_shouldReturnTrueIfExists() {
        User user = new User(null, "To Delete", "delete@example.com", null);
        User created = userService.createUser(user);

        boolean deleted = userService.deleteUser(created.id());
        assertThat(deleted).isTrue();
        assertThat(userService.getUserById(created.id())).isEmpty();
    }

    @Test
    void deleteUser_shouldReturnFalseIfNotExists() {
        boolean deleted = userService.deleteUser(999L);
        assertThat(deleted).isFalse();
    }
}