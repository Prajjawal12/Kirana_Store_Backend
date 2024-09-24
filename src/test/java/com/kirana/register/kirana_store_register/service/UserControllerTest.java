package com.kirana.register.kirana_store_register.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.kirana.register.kirana_store_register.controllers.UserController;
import com.kirana.register.kirana_store_register.exceptions.UserAuthException;
import com.kirana.register.kirana_store_register.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  public void testRegisterUser_Success() throws Exception {
    User user = new User();
    user.setUsername("testuser");
    user.setPassword("testpassword");
    user.setRole("USER");

    when(userService.findByUsername("testuser")).thenReturn(null);
    when(userService.registerUser(any(User.class))).thenReturn(user);

    mockMvc.perform(post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"username\":\"testuser\",\"password\":\"testpassword\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("testuser"));
  }

  @Test
  public void testRegisterUser_UsernameTaken() throws Exception {
    User user = new User();
    user.setUsername("testuser");
    user.setPassword("testpassword");

    when(userService.findByUsername("testuser")).thenReturn(user);

    mockMvc.perform(post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"username\":\"testuser\",\"password\":\"testpassword\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$").value("Username is already taken"));
  }

  @Test
  public void testLoginUser_Success() throws Exception {
    String username = "testuser";
    String password = "testpassword";
    String token = "mockJwtToken";

    when(userService.authenticateUser(username, password)).thenReturn(token);

    mockMvc.perform(post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"username\":\"testuser\",\"password\":\"testpassword\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value(token));
  }

  @Test
  public void testLoginUser_InvalidCredentials() throws Exception {
    String username = "testuser";
    String password = "wrongpassword";

    when(userService.authenticateUser(username, password)).thenThrow(
        new UserAuthException("Invalid username or password", HttpStatus.UNAUTHORIZED));

    mockMvc.perform(post("/api/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"username\":\"testuser\",\"password\":\"wrongpassword\"}"))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$").value("Invalid username or password"));
  }
}
