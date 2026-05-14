package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public Optional<User> getUserById(Long id) {
        return users.stream().filter(user -> user.id().equals(id)).findFirst();
    }

    public User createUser(User user) {
        User newUser = new User(idGenerator.getAndIncrement(), user.name(), user.email(), LocalDateTime.now());
        users.add(newUser);
        return newUser;
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).id().equals(id)) {
                User existing = users.get(i);
                User newUser = new User(id, updatedUser.name(), updatedUser.email(), existing.createdAt());
                users.set(i, newUser);
                return Optional.of(newUser);
            }
        }
        return Optional.empty();
    }

    public boolean deleteUser(Long id) {
        return users.removeIf(user -> user.id().equals(id));
    }
}