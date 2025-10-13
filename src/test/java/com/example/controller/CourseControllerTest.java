// ./mvnw clean test -Dtest=CourseControllerTest

package com.example.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


/**
 * Integration tests for CourseController.
 * Compatible with Java 8 + JUnit 4 + Spring Boot 2.4.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.example.pes.PesApplication.class)
@AutoConfigureMockMvc(addFilters = false)  // 禁用 Spring Security 过滤器
@TestPropertySource(locations = "classpath:application-test.properties")
//@Sql(scripts = "/testdata/course_init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /** 测试：获取所有课程 */
    @Test
    public void testGetAllCourses() throws Exception {
        mockMvc.perform(get("/course/getallc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    /** 测试：按名称模糊查询 */
    @Test
    public void testQueryCname() throws Exception {
        mockMvc.perform(get("/course/querybyname")
                .param("cname", "Database")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cname", containsString("Database")));
    }

    /** 测试：通过课程编号查询 */
    @Test
    public void testGetAdmCrs() throws Exception {
        mockMvc.perform(get("/course/querycno")
                .param("cno", "C001")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /** 测试：新增课程（随机课程号避免重复主键） */
    @Test
    public void testAddCourse() throws Exception {
//        String randomCno = "C" + (System.currentTimeMillis() % 100000);
        String newCourseJson = "{"
                + "\"cno\": \"C010\","
                + "\"cname\": \"AI Systems Advanced\","
                + "\"cterm\": 6,"
                + "\"ccredit\": 4,"
                + "\"cdept\": \"Computer Science\""
                + "}";

        mockMvc.perform(post("/course/savecourse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCourseJson))
                .andExpect(status().isOk());
    }

    /** 测试：更新课程名 */
    @Test
    public void testUpdateCourseName() throws Exception {
        mockMvc.perform(put("/course/updatecname")
                .param("cno", "C002")
                .param("cname", "Maintenance Programming"))
                .andExpect(status().isOk());
    }

    /** 测试：删除课程 */
    @Test
    public void testDeleteCourse() throws Exception {
        mockMvc.perform(delete("/course/deletecourse")
                .param("cno", "C010"))
                .andExpect(status().isOk());
    }
}
