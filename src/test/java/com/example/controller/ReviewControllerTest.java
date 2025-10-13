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
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /** 测试：查询所有评价 */
    @Test
    public void testQueryAllReviews() throws Exception {
        mockMvc.perform(get("/review/queryall"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    /** 测试：按学生号查询评价 */
    @Test
    public void testQueryByStudent() throws Exception {
        mockMvc.perform(get("/review/queryByStu")
                .param("Sno", "S001"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /** 测试：按课程或教师模糊查询 */
    @Test
    public void testQueryReviewByKeyword() throws Exception {
        mockMvc.perform(get("/review/queryreview")
                .param("PCname", "Database"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /** 测试：点赞评价 */
    @Test
    public void testUpvoteReview() throws Exception {
        mockMvc.perform(post("/review/upvote")
                .param("id_review", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(anyOf(is("1"), is("0"))));
    }

    /** 测试：踩评价 */
    @Test
    public void testDownvoteReview() throws Exception {
        mockMvc.perform(post("/review/downvote")
                .param("id_review", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(anyOf(is("1"), is("0"))));
    }

    /** 测试：保存新评价 */
    @Test
    public void testSaveReview() throws Exception {
        String newReviewJson = "{"
                + "\"id_review\": 999,"
                + "\"sno\": \"S001\","
                + "\"pno\": \"P001\","
                + "\"cno\": \"C001\","
                + "\"comment\": \"This is a great course!\","
                + "\"score\": 5"
                + "}";

        mockMvc.perform(post("/review/saveRev")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newReviewJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(anyOf(is("1"), is("0"))));
    }

    /** 测试：举报评价 */
    @Test
    public void testReportReview() throws Exception {
        mockMvc.perform(post("/review/reportRev")
                .param("id_review", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(anyOf(is("1"), is("0"))));
    }

    /** 测试：获取举报列表 */
    @Test
    public void testGetReportedReviews() throws Exception {
        mockMvc.perform(get("/review/getreport"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    /** 测试：获取课程排行 */
    @Test
    public void testGetCourseRank() throws Exception {
        mockMvc.perform(get("/review/getcrank"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /** 测试：获取教师排行 */
    @Test
    public void testGetProfessorRank() throws Exception {
        mockMvc.perform(get("/review/getprank"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
