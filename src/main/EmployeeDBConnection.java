package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class EmployeeDBConnection implements EmployeeDBConnectionInterface {


    public static String URL = "jdbc:sqlserver://localhost:1433;databaseName=EmployeeDB;encrypt=true;trustServerCertificate=true";
    public static String USER = "muneeb";
    public static String PASSWORD = "12345678";

    @Override
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
