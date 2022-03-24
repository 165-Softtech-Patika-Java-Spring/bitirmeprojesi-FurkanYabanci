package com.softtech.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.softtech.market.dto.request.UserSaveRequestDto;
import com.softtech.market.dto.request.UserUpdateRequestDto;
import com.softtech.market.model.User;
import com.softtech.market.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserControllerTest extends BaseControllerTest{

    private static final String BASE_PATH = "/api/v1/users";

    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void save() throws Exception {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();
        userSaveRequestDto.setUsername("Jonny");
        userSaveRequestDto.setFirstName("John");
        userSaveRequestDto.setLastName("Cena");
        userSaveRequestDto.setPassword("12345");

        String content = objectMapper.writeValueAsString(userSaveRequestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(BASE_PATH)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
    }

    @Test
    void update() throws Exception {
     /*   User user = new User();
        user.setId(1l);
        user.setUsername("Joe");
        user.setFirstName("John");
        user.setLastName("Cena");
        user.setPassword("12345");
        userRepository.save(user);

        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto();
        userUpdateRequestDto.setId(1l);
        userUpdateRequestDto.setUsername("Joe");
        userUpdateRequestDto.setFirstName("Jonny");
        userUpdateRequestDto.setLastName("Cena");
        userUpdateRequestDto.setPassword("12345678");

        String content = objectMapper.writeValueAsString(userUpdateRequestDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put(BASE_PATH)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);*/
    }

    @Test
    void delete() throws Exception {
        User user = new User();
        user.setId(1l);
        user.setUsername("Jonny");
        user.setFirstName("John");
        user.setLastName("Cena");
        user.setPassword("12345");
        userRepository.save(user);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete(BASE_PATH+"/1")
                .content("1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);
        assertTrue(isSuccess);
    }
}