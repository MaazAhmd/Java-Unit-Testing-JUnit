package main;

import java.sql.Connection;
import java.sql.SQLException;

public interface EmployeeDBConnectionInterface {
    Connection connect() throws SQLException;
}
