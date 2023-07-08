package com.example.phonecontacts.controller;

import com.example.phonecontacts.dao.interfaces.IContactDao;
import com.example.phonecontacts.dao.interfaces.IUserDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.phonecontacts.constants.TestConstants.getContactDto;
import static com.example.phonecontacts.constants.TestConstants.getTestContact;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {

    MockMvc mockMvc;

    IContactDao contactDao = mock(IContactDao.class);

    IUserDao userDao = mock(IUserDao.class);


    ContactController controller = new ContactController(contactDao, userDao);

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }

    @Test
    void createContactTest() throws Exception {
        given(contactDao.create(getTestContact())).willReturn(getTestContact());
        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getContactDto())))
                .andExpect(status().isOk());
    }

    @Test
    void updateContactTest() throws Exception {
        given(contactDao.update(getTestContact(), getTestContact().getId())).willReturn(getTestContact());
        mockMvc.perform(
                        put("/contacts/{id}",getTestContact().getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(getContactDto())))
                .andExpect(status().isOk());
    }

    @Test
    void deleteContactTest() throws Exception {
        when(contactDao.delete(getTestContact().getId()))
                .thenReturn("Project with id:" + getTestContact().getId() + " was successfully removed");
        MockHttpServletResponse response = mockMvc.perform(
                delete("/contacts/{id}", getTestContact().getId())
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void getAllTest() throws Exception {
        given(contactDao.findAll()).willReturn(List.of(getTestContact()));
        mockMvc.perform(get("/contacts/all"))
                .andExpect(status().isOk());
    }
}