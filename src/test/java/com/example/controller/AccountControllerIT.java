package com.example.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = com.example.pes.PesApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "/testdata/account_init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AccountControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final String baseUrl = "/acc";

    @Test
    @DisplayName("管理员登录成功：应返回 2")
    void testAdminLoginSuccess() throws Exception {
        String loginJson = "{"
                + "\"Sno\":\"A001\","
                + "\"Spw\":\"adminpass\""
                + "}";

        mockMvc.perform(post(baseUrl + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("2"));  // 管理员成功返回2
    }

    @Test
    @DisplayName("管理员登录失败：应返回 0")
    void testAdminLoginFail() throws Exception {
        String loginJson = "{"
                + "\"Sno\":\"A001\","
                + "\"Spw\":\"wrongpass\""
                + "}";

        mockMvc.perform(post(baseUrl + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    @DisplayName("学生登录成功：应返回 1")
    void testStudentLoginSuccess() throws Exception {
        String loginJson = "{"
                + "\"Sno\":\"S001\","
                + "\"Spw\":\"passwd\""
                + "}";

        mockMvc.perform(post(baseUrl + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    @DisplayName("学生登录失败：应返回 0")
    void testStudentLoginFail() throws Exception {
        String loginJson = "{"
                + "\"Sno\":\"S001\","
                + "\"Spw\":\"wrongpass\""
                + "}";

        mockMvc.perform(post(baseUrl + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }
}
