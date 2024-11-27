package main;

public class EmployeeDAOMain {
    public static void main(String[] args) {
        EmployeeDBConnection dbConnection = new EmployeeDBConnection();
        EmployeeDAO employeeDAO = new EmployeeDAO(dbConnection);

        // Example usage
        if (employeeDAO.addEmployee(4, "Muneeb")) {
            System.out.println("Employee added successfully.");
        } else {
            System.out.println("Failed to add employee.");
        }

        String employeeName = employeeDAO.getEmployeeName(1);
        if (employeeName != null) {
            System.out.println("Employee name: " + employeeName);
        } else {
            System.out.println("Employee not found.");
        }
    }
}
