package com.example.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Liuyujia
 * @create 2025-09-27 19:58
 */

@RestController
public class DbHealthController {
//    判断docker mysql是否连接正常

    private final JdbcTemplate jdbc;
    public DbHealthController(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @GetMapping("/health/db/full")
    public String dbFull() {
        String dbName = jdbc.queryForObject("SELECT DATABASE()", String.class);
        Integer studentCnt = jdbc.queryForObject("SELECT COUNT(*) FROM student", Integer.class);
        Integer reviewCnt = jdbc.queryForObject("SELECT COUNT(*) FROM review", Integer.class);
        Integer courseCnt = jdbc.queryForObject("SELECT COUNT(*) FROM course", Integer.class);
        Integer professorCnt = jdbc.queryForObject("SELECT COUNT(*) FROM professor", Integer.class);

        return String.format(
                "Connected to DB: %s | student=%d | review=%d | course=%d | professor=%d",
                dbName, studentCnt, reviewCnt, courseCnt, professorCnt
        );
    }
}
