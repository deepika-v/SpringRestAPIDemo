package com.example.demo.controller.v1;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsers_shouldReturnList() throws Exception {
        User user1 = new User(1L, "John", "john@example.com", LocalDateTime.now());
        User user2 = new User(2L, "Jane", "jane@example.com", LocalDateTime.now());

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John"));
    }

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        User user = new User(1L, "John", "john@example.com", LocalDateTime.now());
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void getUserById_shouldReturn404() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser_shouldReturnCreatedUser() throws Exception {
        User input = new User(null, "John", "john@example.com", null);
        User created = new User(1L, "John", "john@example.com", LocalDateTime.now());

        when(userService.createUser(any(User.class))).thenReturn(created);

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void createUser_shouldReturn400ForInvalidInput() throws Exception {
        User input = new User(null, "", "invalid-email", null);

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {
        User input = new User(null, "Updated", "updated@example.com", null);
        User updated = new User(1L, "Updated", "updated@example.com", LocalDateTime.now());

        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void updateUser_shouldReturn404() throws Exception {
        User input = new User(null, "Updated", "updated@example.com", null);

        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_shouldReturn204() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/v1/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_shouldReturn404() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(false);

        mockMvc.perform(delete("/v1/users/1"))
                .andExpect(status().isNotFound());
    }
}