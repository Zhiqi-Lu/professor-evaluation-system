package com.example.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Integration tests for ProfessorController.
 * Compatible with Java 8 + JUnit 4 + Spring Boot 2.4.
 */
@RunWith(org.springframework.test.context.junit4.SpringRunner.class)
@SpringBootTest(classes = com.example.pes.PesApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProfessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /** 测试：查询所有教师 */
    @Test
    public void testQueryAllProfessors() throws Exception {
        mockMvc.perform(get("/professor/queryall")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$[0].pno", notNullValue()));
    }

    /** 测试：按教师号查询教师 */
    @Test
    public void testGetProfessorByPno() throws Exception {
        mockMvc.perform(get("/professor/queryprofessor")
                .param("Pno", "P001")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pno", is("P001")));
    }

    /** 测试：更新教师所属学院 */
    @Test
    public void testUpdateProfessorDept() throws Exception {
        mockMvc.perform(put("/professor/updatepdept")
                .param("Pno", "P001")
                .param("Pdept", "Engineering"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(anyOf(is("1"), is("0")))); // 受影响行数
    }

    /** 测试：更新教师职位 */
    @Test
    public void testUpdateProfessorPosition() throws Exception {
        mockMvc.perform(put("/professor/updateppos")
                .param("Pno", "P001")
                .param("Ppos", "Professor"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(anyOf(is("1"), is("0"))));
    }

    /** 测试：通过教师姓名查询教师号 */
    @Test
    public void testGetPnoByName() throws Exception {
        mockMvc.perform(get("/professor/querypno")
                .param("Pname", "Dr. Zhang")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(emptyOrNullString())));
    }

    /** 测试：添加教师 */
    @Test
    public void testAddProfessor() throws Exception {
        String newProfessorJson = "{"
                + "\"pno\": \"P099\","
                + "\"pname\": \"Test Professor\","
                + "\"ppos\": \"Lecturer\","
                + "\"pinfo\": \"Nice\","
                + "\"pdept\": \"Engineering\""
                + "}";

        mockMvc.perform(post("/professor/savePrf")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newProfessorJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(anyOf(is("1"), is("0"))));
    }

    /** 测试：删除教师 */
    @Test
    public void testDeleteProfessor() throws Exception {
        mockMvc.perform(delete("/professor/deletePrf")
                .param("Pno", "P099"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(anyOf(is("1"), is("0"))));
    }
}
