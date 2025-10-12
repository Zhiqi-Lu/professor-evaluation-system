package com.example.pes;

import com.example.dao.AdminDao;
import com.example.pojo.Administrator;
import com.example.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AdminServiceImplTest {

    @Mock
    private AdminDao adminDao;

    @InjectMocks
    private AdminServiceImpl adminService;

    public AdminServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckCredential_CorrectPassword() {
        Administrator admin = new Administrator();
        admin.setApw("admin123");

        when(adminDao.getAdmin("A001")).thenReturn(admin);

        boolean result = adminService.checkCredential("A001", "admin123");
        assertTrue(result);
    }

    @Test
    void testCheckCredential_WrongPassword() {
        Administrator admin = new Administrator();
        admin.setApw("admin123");

        when(adminDao.getAdmin("A001")).thenReturn(admin);

        boolean result = adminService.checkCredential("A001", "wrong");
        assertFalse(result);
    }

    @Test
    void testCheckCredential_AdminNotFound() {
        when(adminDao.getAdmin("A002")).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            adminService.checkCredential("A002", "admin123");
        });
    }
}