package com.example.phonecontacts.controller;

import com.example.phonecontacts.authorization.interfaces.IAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.phonecontacts.constants.TestConstants.getLoginDto;
import static com.example.phonecontacts.constants.TestConstants.getSignupDto;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    MockMvc mockMvc;

    IAuth auth = mock(IAuth.class);
    AuthController controller = new AuthController(auth);

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void authenticateUserTest() throws Exception {
        given(auth.login(getLoginDto())).willReturn("User signed-in successfully!");
        mockMvc.perform(
                        post("/api/auth/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(getLoginDto())))
                .andExpect(status().isOk());
    }

    @Test
    void registerUserTest() throws Exception {
        given(auth.register(getSignupDto())).willReturn("User signed-in successfully!");
        mockMvc.perform(
                        post("/api/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(getSignupDto())))
                .andExpect(status().isOk());
    }


}