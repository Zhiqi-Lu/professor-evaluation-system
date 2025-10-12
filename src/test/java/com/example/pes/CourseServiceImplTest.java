package com.example.pes;

import com.example.dao.CourseDao;
import com.example.pojo.Course;
import com.example.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CourseServiceImplTest {

    @Mock
    private CourseDao courseDao;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        course = new Course();
        course.setCno("C001");
        course.setCname("Data Engineering");
        course.setCcredit(5);
    }

    @Test
    void testGetAllCourses() {
        List<Course> mockCourses = Arrays.asList(course);
        when(courseDao.getallc()).thenReturn(mockCourses);

        List<Course> result = courseService.getallc();

        assertEquals(1, result.size());
        assertEquals("Data Engineering", result.get(0).getCname());
        verify(courseDao, times(1)).getallc();
    }

    @Test
    void testQueryCname() {
        when(courseDao.queryCname("Data Engineering")).thenReturn(Arrays.asList(course));

        List<Course> result = courseService.queryCname("Data Engineering");

        assertEquals(1, result.size());
        assertEquals("C001", result.get(0).getCno());
        verify(courseDao).queryCname("Data Engineering");
    }

    @Test
    void testGetAdmCrs() {
        when(courseDao.getAdmCrs("C001")).thenReturn(course);

        Course result = courseService.getAdmCrs("C001");

        assertNotNull(result);
        assertEquals("Data Engineering", result.getCname());
        verify(courseDao).getAdmCrs("C001");
    }

    @Test
    void testSaveCourse() {
        when(courseDao.savecourse(course)).thenReturn(1);

        int result = courseService.savecourse(course);

        assertEquals(1, result);
        verify(courseDao).savecourse(course);
    }

    @Test
    void testUpdateCredit() {
        when(courseDao.updatecredit(anyMap())).thenReturn(1);

        int result = courseService.updatecredit("C001", 6);

        assertEquals(1, result);
        // 验证参数map中确实包含正确的键值
        ArgumentCaptor<Map> captor = ArgumentCaptor.forClass(Map.class);
        verify(courseDao).updatecredit(captor.capture());
        assertEquals("C001", captor.getValue().get("cno"));
        assertEquals(6, captor.getValue().get("ccredit"));
    }

    @Test
    void testDeleteCourse() {
        when(courseDao.deletecourse("C001")).thenReturn(1);

        int result = courseService.deletecourse("C001");

        assertEquals(1, result);
        verify(courseDao).deletecourse("C001");
    }
}
