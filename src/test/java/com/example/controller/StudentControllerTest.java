package com.example.controller;

import com.example.pes.PesApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PesApplication.class)
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /** 测试：查询所有学生 */
    @Test
    public void testQueryAllStudents() throws Exception {
        mockMvc.perform(get("/student/queryall"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    /** 测试：按学号查询学生 */
    @Test
    public void testQueryStudentBySno() throws Exception {
        mockMvc.perform(get("/student/querystudent")
                .param("Sno", "S001"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /** 测试：更新昵称 */
    @Test
    public void testUpdateStudentNickname() throws Exception {
        mockMvc.perform(put("/student/updatesnickname")
                .param("Sno", "S001")
                .param("Snickname", "CoderYu"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(anyOf(is("0"), is("1"))));
    }

    /** 测试：更新密码 */
    @Test
    public void testUpdateStudentPassword() throws Exception {
        mockMvc.perform(put("/student/updatespw")
                .param("Sno", "S001")
                .param("Spw", "newpass123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(anyOf(is("0"), is("1"))));
    }

    /** 测试：添加学生 */
    @Test
    public void testSaveStudent() throws Exception {
        String newStudentJson = "{"
                + "\"sno\": \"S099\","
                + "\"sname\": \"Test Student\","
                + "\"spw\": \"123456\","
                + "\"sdept\": \"Computer Science\""
                + "}";

        mockMvc.perform(post("/student/saveStu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newStudentJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(anyOf(is("1"), is("0"))));
    }

    /** 测试：删除学生 */
    @Test
    public void testDeleteStudent() throws Exception {
        mockMvc.perform(delete("/student/deleteStu")
                .param("Sno", "S099"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(anyOf(is("0"), is("1"))));
    }
}
