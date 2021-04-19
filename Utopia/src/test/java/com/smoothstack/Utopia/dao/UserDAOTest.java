package com.smoothstack.Utopia.dao;

import com.smoothstack.Utopia.data.users.User;
import com.smoothstack.Utopia.data.users.UserRole;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    private static UserDAO userDAO;
    @BeforeAll
    static void beforeAll() throws SQLException, ClassNotFoundException {
        userDAO = new UserDAO("utopia_test");
    }

    @AfterAll
    static void afterAll() throws SQLException {
        userDAO.close();
    }

    @Test
    void roleFromIDTest() throws SQLException {
        //Given values: (id=1, name='ADMIN'), (id=2, name='CUSTOMER'), (id=3, name='AGENT')
        UserRole admin = userDAO.roleFromID(1);
        UserRole customer = userDAO.roleFromID(2);
        UserRole agent = userDAO.roleFromID(3);
        assertNotNull(admin);
        assertNotNull(customer);
        assertNotNull(agent);
        assertEquals("ADMIN", admin.getName());
        assertEquals("CUSTOMER", customer.getName());
        assertEquals("AGENT", agent.getName());
    }

    @Test
    void userFromIDTest() throws SQLException {
        //Given value: 2,2,given2,family2,user2,user2@test.test,$2b$10$ZjRvQ7GnNHEaBRpNZxAnRuHhUqcJ4Rphq2wltOD9N2hXKddYJBoIK,23334445555
        User user2 = userDAO.userFromID(2);
        assertNotNull(user2);
        assertEquals(2, user2.getId());
        assertEquals(2, user2.getRole().getId());
        assertEquals("given2", user2.getGiven_name());
        assertEquals("family2", user2.getFamily_name());
        assertEquals("user2@test.test", user2.getEmail());
        assertEquals("user2", user2.getUsername());
        assertEquals("$2b$10$ZjRvQ7GnNHEaBRpNZxAnRuHhUqcJ4Rphq2wltOD9N2hXKddYJBoIK", user2.getPassword());
        assertEquals("23334445555", user2.getPhone());
        //Given value: 4,2,given4,family4,user4,user4@test.test,$2b$10$pKOhkeAlum7VaB9/4kesbONjiYMdM/8igLTLlRgTJ/joFGN0imptC,45556667777
        User user4 = userDAO.userFromID(4);
        assertNotNull(user4);
        assertEquals(4, user4.getId());
        assertEquals(2, user4.getRole().getId());
        assertEquals("given4", user4.getGiven_name());
        assertEquals("family4", user4.getFamily_name());
        assertEquals("user4@test.test", user4.getEmail());
        assertEquals("user4", user4.getUsername());
        assertEquals("$2b$10$pKOhkeAlum7VaB9/4kesbONjiYMdM/8igLTLlRgTJ/joFGN0imptC", user4.getPassword());
        assertEquals("45556667777", user4.getPhone());
        //No user; should be null
        User usernull = userDAO.userFromID(-1);
        assertNull(usernull);
    }

    @Test
    void userFromUsernameTest() throws SQLException {
        //Given value: 2,2,given2,family2,user2,user2@test.test,$2b$10$ZjRvQ7GnNHEaBRpNZxAnRuHhUqcJ4Rphq2wltOD9N2hXKddYJBoIK,23334445555
        User user2 = userDAO.userFromUsername("user2");
        assertNotNull(user2);
        assertEquals(2, user2.getId());
        assertEquals(2, user2.getRole().getId());
        assertEquals("given2", user2.getGiven_name());
        assertEquals("family2", user2.getFamily_name());
        assertEquals("user2@test.test", user2.getEmail());
        assertEquals("user2", user2.getUsername());
        assertEquals("$2b$10$ZjRvQ7GnNHEaBRpNZxAnRuHhUqcJ4Rphq2wltOD9N2hXKddYJBoIK", user2.getPassword());
        assertEquals("23334445555", user2.getPhone());
        //Given value: 4,2,given4,family4,user4,user4@test.test,$2b$10$pKOhkeAlum7VaB9/4kesbONjiYMdM/8igLTLlRgTJ/joFGN0imptC,45556667777
        User user4 = userDAO.userFromUsername("user4");
        assertNotNull(user4);
        assertEquals(4, user4.getId());
        assertEquals(2, user4.getRole().getId());
        assertEquals("given4", user4.getGiven_name());
        assertEquals("family4", user4.getFamily_name());
        assertEquals("user4@test.test", user4.getEmail());
        assertEquals("user4", user4.getUsername());
        assertEquals("$2b$10$pKOhkeAlum7VaB9/4kesbONjiYMdM/8igLTLlRgTJ/joFGN0imptC", user4.getPassword());
        assertEquals("45556667777", user4.getPhone());
        //No user; should be null
        User usernull = userDAO.userFromUsername("erhgksguhklsdfghk");
        assertNull(usernull);
    }

    @Test
    void insertDirectTest() throws SQLException {
        int id_newuser = userDAO.insertDirect(1, "Emily", "DelHasse", "emilyy", "emily@gmail.com", "uwupassword42", "(385) 438-9442");
        assertTrue(id_newuser >= 0);
        assertEquals("emilyy", userDAO.userFromID(id_newuser).getUsername());
        userDAO.rollback();
    }

    @Test
    void insertRoleTest() throws SQLException {
        //TODO: Unused Method
    }

    @Test
    void getAllTest() throws SQLException {
        ArrayList<User> all = userDAO.getAll();
        assertEquals(7, all.size());
    }

    @Test
    void getAllRolesTest() {
        //TODO: Unused Method
    }
}