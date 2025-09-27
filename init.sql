CREATE DATABASE professorevaluation;
USE professorevaluation;

DROP TABLE IF EXISTS SCR;
DROP TABLE IF EXISTS PCR;
DROP TABLE IF EXISTS Review;
DROP TABLE IF EXISTS Professor;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS Administrator;
DROP TABLE IF EXISTS Student;

CREATE TABLE student (
    Sno VARCHAR(10) NOT NULL PRIMARY KEY,
    Sname VARCHAR(10) NOT NULL,
    Snickname VARCHAR(20) NOT NULL,
    Spw VARCHAR(20) NOT NULL,
    Syear YEAR NOT NULL,
    Sgender VARCHAR(2),
    Smail VARCHAR(30) NOT NULL UNIQUE,
    Svalid DATE NOT NULL,
    Sstate DATE,
    UNIQUE (Sno)
);

CREATE TABLE administrator (
    Ano VARCHAR(10) NOT NULL PRIMARY KEY,
    Amail VARCHAR(30) NOT NULL UNIQUE,
    Anickname VARCHAR(20) NOT NULL UNIQUE,
    Apw VARCHAR(20) NOT NULL,
    Aprivilege INT NOT NULL,
    UNIQUE (Ano)
);

CREATE TABLE course (
    Cno VARCHAR(10) NOT NULL PRIMARY KEY,
    Cname VARCHAR(20) NOT NULL UNIQUE,
    Cterm INT NOT NULL,
    Ccredit INT NOT NULL,
    Cdept VARCHAR(20) NOT NULL,
    UNIQUE (Cno)
);

CREATE TABLE professor (
    Pno VARCHAR(10) NOT NULL PRIMARY KEY,
    Pname VARCHAR(10) NOT NULL,
    Ppos VARCHAR(10) NOT NULL,
    Pinfo TEXT,
    Pdept VARCHAR(20) NOT NULL,
    UNIQUE (Pno)
);

CREATE TABLE review (
    id_review INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Sno VARCHAR(10) NOT NULL,
    Pno VARCHAR(10) NOT NULL,
    Cno VARCHAR(10) NOT NULL,
    Revt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Comment TEXT NOT NULL,
    personality INT NOT NULL,
    difficulty INT NOT NULL,
    quality INT NOT NULL,
    state INT NOT NULL,
    downvote INT NOT NULL DEFAULT 0,
    upvote INT NOT NULL DEFAULT 0
);

CREATE TABLE pcr (
    Pno VARCHAR(10) NOT NULL,
    Cno VARCHAR(10) NOT NULL,
    PRIMARY KEY (Pno, Cno)
);

CREATE TABLE scr (
    Sno VARCHAR(10) NOT NULL,
    Cno VARCHAR(10) NOT NULL,
    PRIMARY KEY (Sno, Cno)
);

INSERT INTO student (Sno, Sname, Snickname, Spw, Syear, Sgender, Smail, Svalid, Sstate)
VALUES
('S001', 'Alice', 'Ali', 'passwd', 2019, 'F', 'alice@example.com', '2025-12-31', NULL),
('S002', 'Bob', 'Bobby', 'passwd', 2020, 'M', 'bob@example.com', '2025-12-31', NULL),
('S003', 'Charlie', 'Char', 'passwd', 2020, 'M', 'charlie@example.com', '2025-12-31', NULL),
('S004', 'Diana', 'Di', 'passwd', 2022, 'F', 'diana@example.com', '2025-12-31', NULL),
('S005', 'Ethan', 'E', 'passwd', 2019, 'M', 'ethan@example.com', '2025-12-31', NULL),
('S006', 'Fiona', 'Fi', 'passwd', 2021, 'F', 'fiona@example.com', '2025-12-31', NULL);

INSERT INTO administrator (Ano, Amail, Anickname, Apw, Aprivilege)
VALUES
('A001', 'admin1@example.com', 'root1', 'adminpass', 1);

INSERT INTO course (Cno, Cname, Cterm, Ccredit, Cdept)
VALUES
('C001', 'Database Systems', 3, 4, 'Computer Science'),
('C002', 'Operating Systems', 4, 5, 'Computer Science'),
('C003', 'Computer Network', 4, 5, 'Computer Science');

INSERT INTO professor (Pno, Pname, Ppos, Pinfo, Pdept)
VALUES
('P001', 'Dr. Zhang', 'professor', 'Expert in databases', 'Computer Science'),
('P002', 'Dr. Li', 'professor', 'Specialist in operating systems', 'Computer Science');