package com.smoothstack.Utopia.dao;

import com.smoothstack.Utopia.util.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class BaseDAOTest {
    /**
     * A simple POJO for testing
     */
    private static class TestDT {
        public String data;
        public Integer id;

        public TestDT(String data) {
            this(data, null);
        }

        public TestDT(String data, Integer id) {
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestDT testDT = (TestDT) o;
            return data.equals(testDT.data) && Objects.equals(id, testDT.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data, id);
        }
    }

    private static class TestDAO extends BaseDAO<TestDT> {
        PreparedStatement insert, delete, get, update;
        /**
         * Initializes the internal connection ready for use
         *
         * @throws SQLException           The connection could not be made.
         * @throws ClassNotFoundException The database driver could not be loaded.
         */
        public TestDAO() throws SQLException, ClassNotFoundException {
            super();
        }

        /**
         * @param rs The result set
         * @return A TestDT
         * @throws SQLException Could not get from resultset
         */
        @Override
        protected TestDT convertTo(ResultSet rs) throws SQLException {
            return new TestDT(rs.getString("data_str"));
        }

        /**
         * Insert the object into the database. ID is ignored and auto-assigned to new value.
         * @param target The object to add. Should not be modified, else risk of heap pollution occurs.
         * @return A PreparedStatement to insert a single object
         * @throws SQLException Could not insert
         */
        @Override
        protected PreparedStatement convertInsert(TestDT target) throws SQLException {
            if (insert == null) {
                insert = this.connection.prepareStatement("INSERT INTO test_dao (data_str) VALUES (?)");
            }
            insert.setString(1, target.data);
            return insert;
        }

        /**
         * @param target The object to remove. Should not be modified, else risk of heap pollution occurs.
         * @return
         * @throws SQLException
         */
        @Override
        protected PreparedStatement convertDelete(TestDT target) throws SQLException {
            if (delete == null) {
                delete = this.connection.prepareStatement("DELETE FROM test_dao WHERE data_str = ?");
            }
            delete.setString(1, target.data);
            return delete;
        }

        /**
         * @param targetOld The old object with old values
         * @param targetNew The new object with new values
         * @return
         * @throws SQLException
         */
        @Override
        protected PreparedStatement convertUpdate(TestDT targetOld, TestDT targetNew) throws SQLException {
            if (update == null) {
                update = this.connection.prepareStatement("UPDATE test_dao SET data_str=? WHERE data_str=?");
            }
            update.setString(1, targetNew.data);
            update.setString(2, targetOld.data);
            return update;
        }
    }

    //Persistent variables
    //This is used to set up and tear down the test table
    private static TestDAO testdao;

    @BeforeAll
    static void beforeAll() throws SQLException, ClassNotFoundException {
        Connection db_connection = DatabaseConnection.getConnection();
        db_connection.setAutoCommit(true);
        testdao = new TestDAO();

        Statement stmt = db_connection.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS test_dao (" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "data_str VARCHAR(255) NOT NULL," +
                "PRIMARY KEY(id)" +
                ");");
        stmt.execute("INSERT INTO test_dao (data_str) VALUES ('initial_data');");
        db_connection.close();
    }

    @SuppressWarnings("SqlWithoutWhere")
    @AfterAll
    static void afterAll() throws SQLException, ClassNotFoundException {
        Connection db_connection = DatabaseConnection.getConnection();
        db_connection.setAutoCommit(true);
        Statement stmt = db_connection.createStatement();
        stmt.execute("DELETE FROM test_dao");
        stmt.close();
        db_connection.close();
    }

    @Test
    void selectTest() throws SQLException {
        //This would normally be done within the testdao class, not outside of it
        PreparedStatement ps = testdao.connection.prepareStatement("SELECT * FROM test_dao WHERE data_str='initial_data'");
        ArrayList<TestDT> arrayList = testdao.select(ps);
        AtomicBoolean found = new AtomicBoolean(false);
        arrayList.forEach(x -> {
            if(x.data.equals("initial_data")) found.set(true);
        });

       assertTrue(found.get());
    }

    @Test
    void createTest() throws SQLException {
        testdao.create(new TestDT("Test123456"), new TestDT("Test654321"));
        testdao.commit();
        ArrayList<TestDT> res = testdao.select(testdao.connection.prepareStatement("SELECT * FROM test_dao"));
        assertTrue(res.contains(new TestDT("Test123456")));
        assertTrue(res.contains(new TestDT("Test654321")));
    }

    @Test
    void updateTest() throws SQLException {
        TestDT old = new TestDT("SomeOldValue"), newer = new TestDT("SomeNewValue");
        testdao.create(old);
        testdao.commit();
        int affected = testdao.update(old, newer);
        testdao.commit();
        assertEquals(1, affected);
        ArrayList<TestDT> res = testdao.select(testdao.connection.prepareStatement("SELECT * FROM test_dao"));
        assertFalse(res.contains(old));
        assertTrue(res.contains(newer));
    }

    @Test
    void deleteTest() throws SQLException {
        TestDT target = new TestDT("garbage");
        testdao.create(target);
        testdao.commit();
        testdao.delete(target);
        testdao.commit();
        ArrayList<TestDT> res = testdao.select(testdao.connection.prepareStatement("SELECT * FROM test_dao"));
        assertFalse(res.contains(target));
    }

    @Test
    void rollbackTest() throws SQLException {
        TestDT target = new TestDT("invisible");
        testdao.create(target);
        testdao.rollback();
        ArrayList<TestDT> res = testdao.select(testdao.connection.prepareStatement("SELECT * FROM test_dao"));
        assertFalse(res.contains(target));
    }

    @Test
    void convertToTest() throws SQLException {
        ResultSet rs = testdao.connection.prepareStatement("SELECT * FROM test_dao WHERE data_str = 'initial_data';").executeQuery();
        assertTrue(rs.next());
        TestDT test = testdao.convertTo(rs);
        //We can't possibly know the ID; just test the data_str
        assertEquals("initial_data", test.data);
    }
}