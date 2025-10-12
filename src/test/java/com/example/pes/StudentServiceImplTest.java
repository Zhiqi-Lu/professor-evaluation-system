package com.example.pes;

import com.example.dao.StudentDao;
import com.example.pojo.Student;
import com.example.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    @Mock
    private StudentDao studentDao;

    @InjectMocks
    private StudentServiceImpl studentService;

    public StudentServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckCredential_ValidPassword() {
        Student mockStudent = mock(Student.class);
        when(studentDao.getStu("S001")).thenReturn(mockStudent);
        when(mockStudent.checkSpw("123456")).thenReturn(true);

        boolean result = studentService.checkCredential("S001", "123456");

        assertTrue(result);
        verify(studentDao).getStu("S001");
        verify(mockStudent).checkSpw("123456");
    }

    @Test
    void testCheckCredential_InvalidPassword() {
        Student mockStudent = mock(Student.class);
        when(studentDao.getStu("S001")).thenReturn(mockStudent);
        when(mockStudent.checkSpw("wrong")).thenReturn(false);

        boolean result = studentService.checkCredential("S001", "wrong");

        assertFalse(result);
    }

    @Test
    void testCheckCredential_StudentNotFound() {
        when(studentDao.getStu("S002")).thenReturn(null);

        // 模拟数据库中没有该学生时应抛出 NullPointerException
        assertThrows(NullPointerException.class, () -> {
            studentService.checkCredential("S002", "123456");
        });
    }
}
