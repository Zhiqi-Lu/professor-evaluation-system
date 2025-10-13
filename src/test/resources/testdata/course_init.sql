-- 初始化课程表数据（供集成测试使用）
DELETE FROM course;

INSERT INTO course (Cno, Cname, Cterm, Ccredit, Cdept)
VALUES
('C011', 'Advanced Database Systems', 4, 3, 'Computer Science'),
('C012', 'Advanced Operating Systems', 4, 4, 'Computer Science'),
('C013', 'Advanced Software Engineering', 5, 3, 'Computer Science');
