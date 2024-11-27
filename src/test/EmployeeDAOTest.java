package test;

import main.EmployeeDAO;
import main.EmployeeDBConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeDAOTest {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private EmployeeDAO employeeDAO;

    @BeforeEach
    void setup() throws Exception {
        // Mock dependencies
        EmployeeDBConnection dbConnection = mock(EmployeeDBConnection.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        // Stub the connect method of dbConnection
        when(dbConnection.connect()).thenReturn(connection);

        // Initialize the EmployeeDAO with the mocked dbConnection
        employeeDAO = new EmployeeDAO(dbConnection);
    }

    @Test
    void testAddEmployee() throws Exception {
        // Stub methods for addEmployee
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Test addEmployee
        boolean result = employeeDAO.addEmployee(1, "John Doe");
        assertTrue(result);

        // Verify interactions
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).setString(2, "John Doe");
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testGetEmployeeName() throws Exception {
        // Stub methods for getEmployeeName
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("name")).thenReturn("John Doe");

        // Test getEmployeeName
        String name = employeeDAO.getEmployeeName(1);
        assertEquals("John Doe", name);

        // Verify interactions
        verify(preparedStatement).setInt(1, 1);
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getString("name");
    }
}
