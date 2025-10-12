-- 清空原有数据
DELETE FROM administrator;
DELETE FROM student;

-- 插入测试管理员
INSERT INTO administrator (Ano, Amail, Anickname, Apw, Aprivilege)
VALUES
('A001', 'admin1@example.com', 'root1', 'adminpass', 1);

-- 插入测试学生
INSERT INTO student (Sno, Sname, Snickname, Spw, Syear, Sgender, Smail, Svalid, Sstate)
VALUES
('S001', 'Alice', 'Ali', 'passwd', 2019, 'F', 'alice@example.com', '2025-12-31', NULL);
