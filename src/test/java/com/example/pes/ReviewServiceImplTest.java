package com.example.pes;



import com.example.dao.ReviewDao;
import com.example.pojo.Review;
import com.example.pojo.CourseRank;
import com.example.pojo.ProfessorRank;
import com.example.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    @Mock
    private ReviewDao reviewDao;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTryDele_WhenAlreadyReported_ShouldReturnFalse() {
        // Assume that the review state in the database is 3 (reported)
        Review review = new Review();
        review.setState(3);
        when(reviewDao.getRev(1)).thenReturn(review);

        boolean result = reviewService.tryDele(1);

        assertFalse(result);
        verify(reviewDao, never()).modState(anyInt(), anyInt()); // State should not be modified
    }

    @Test
    void testTryDele_WhenNormal_ShouldUpdateAndReturnTrue() {
        Review review = new Review();
        review.setState(1);
        when(reviewDao.getRev(2)).thenReturn(review);

        boolean result = reviewService.tryDele(2);

        assertTrue(result); // Expectation: Return true
        verify(reviewDao).modState(2, 2); // The status should be changed to 2
    }

    @Test
    void testReportRev_WhenValid_ShouldChangeState() {
        Review review = new Review();
        review.setState(1);
        when(reviewDao.getRev(10)).thenReturn(review);

        boolean result = reviewService.reportRev(10);

        assertTrue(result);
        verify(reviewDao).modState(10, 3);
    }

    @Test
    void testReportRev_WhenAlreadyReported_ShouldReturnFalse() {
        Review review = new Review();
        review.setState(3);
        when(reviewDao.getRev(20)).thenReturn(review);

        boolean result = reviewService.reportRev(20);

        assertFalse(result);
        verify(reviewDao, never()).modState(anyInt(), anyInt());
    }

    @Test
    void testGetCourseRank_WhenMoreThan10_ShouldReturn10() {
        List<CourseRank> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new CourseRank());
        }
        when(reviewDao.getCourseRank()).thenReturn(list);

        List<CourseRank> result = reviewService.getCourseRank();

        assertEquals(10, result.size());
    }

    @Test
    void testGetCourseRank_WhenLessThan10_ShouldReturnAll() {
        List<CourseRank> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new CourseRank());
        }
        when(reviewDao.getCourseRank()).thenReturn(list);

        List<CourseRank> result = reviewService.getCourseRank();

        assertEquals(5, result.size());
    }
}

