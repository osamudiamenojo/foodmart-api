package com.example.food.controllers;

import com.example.food.dto.EditUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testEditUserDetails() throws Exception {
       EditUserDto editUserDto = new EditUserDto();
       editUserDto.setFirstName("Gisung");
       editUserDto.setLastName("Kefas");
       editUserDto.setEmail("kefas@gmail.com");
       editUserDto.setDateOfBirth(LocalDate.ofEpochDay(07-02-2020));

       String requestBody = mapper.writeValueAsString(editUserDto);

       mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/editUser")
                       .contentType("application/json").content(requestBody))
                    .andExpect(status().isAccepted());

    }
}