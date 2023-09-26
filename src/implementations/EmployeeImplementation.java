package implementations;
import interfaces.EmployeeInterface;
import helper.DatabaseConnection;
import dto.Employee;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Random;

public class EmployeeImplementation implements EmployeeInterface{
    private DatabaseConnection db;

    public EmployeeImplementation() {
        db = DatabaseConnection.getInstance();
    }
    public String generateRandomMatricule() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder matricule = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(characters.length());
            matricule.append(characters.charAt(index));
        }

        return matricule.toString();
    }

    @Override
    public Employee add(Employee employee) {
        // Generate a random matricule
        String generatedMatricule = generateRandomMatricule();
        employee.setMatricule(generatedMatricule);

        // Set hiring date from the current system's LocalDate
        LocalDate currentDate = LocalDate.now();
        employee.setHiringDate(currentDate);

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO employee (matricule, name, prenoun, birthDate, phone, hiringDate, email) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );

            preparedStatement.setString(1, employee.getMatricule());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getPrenoun());
            preparedStatement.setDate(4, java.sql.Date.valueOf(employee.getBirthDate()));
            preparedStatement.setString(5, employee.getPhone());
            preparedStatement.setDate(6, java.sql.Date.valueOf(currentDate));
            preparedStatement.setString(7, employee.getEmail());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                return employee; // Return the inserted employee object
            } else {
                return null; // Insert failed
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Exception occurred
        }
    }

    @Override
    public Employee searchByMatricule(String matricule) {
        try {
            Connection conn = db.getConnection();
            String searchQuery = "SELECT * FROM employee WHERE matricule = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(searchQuery)) {
                preparedStatement.setString(1, matricule);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Retrieve employee data from the database
                    String foundMatricule = resultSet.getString("matricule");
                    String name = resultSet.getString("name");
                    String prenoun = resultSet.getString("prenoun");
                    String email = resultSet.getString("email");
                    String phone = resultSet.getString("phone");
                    LocalDate birthdate = resultSet.getDate("birthdate").toLocalDate();
                    LocalDate hiringdate = resultSet.getDate("hiringdate").toLocalDate();
                    return new Employee(name, prenoun, birthdate, phone, foundMatricule, hiringdate, email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean deleteByMatricule(String deleteMatricule) {
        Connection conn = db.getConnection();
        String deleteQuery = "DELETE FROM employee WHERE matricule = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);

            preparedStatement.setString(1, deleteMatricule); // Use the parameter deleteMatricule

            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        Connection conn = db.getConnection();
        String selectQuery = "SELECT * FROM employee";
        List<Employee> employees = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();



            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setMatricule(resultSet.getString("matricule"));
                employee.setName(resultSet.getString("name"));
                employee.setPrenoun(resultSet.getString("prenoun"));

                employee.setPhone(resultSet.getString("phone"));
                employee.setBirthDate(resultSet.getDate("birthdate").toLocalDate());
                employee.setHiringDate(resultSet.getDate("hiringdate").toLocalDate());
                employee.setEmail(resultSet.getString("email"));
                employees.add(employee);
            }

            return employees;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public Employee update(Employee employeeToUpdate){
        Connection conn = db.getConnection();
        String updateQuery = "UPDATE employee SET name = ?, prenoun = ?, email = ?, phone = ?, birthdate = ?, hiringdate = ? WHERE matricule = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);

            preparedStatement.setString(1, employeeToUpdate.getName());
            preparedStatement.setString(2, employeeToUpdate.getPrenoun());
            preparedStatement.setString(3, employeeToUpdate.getEmail());
            preparedStatement.setString(4, employeeToUpdate.getPhone());

            preparedStatement.setDate(5, java.sql.Date.valueOf(employeeToUpdate.getBirthDate()));
            preparedStatement.setDate(6, java.sql.Date.valueOf(employeeToUpdate.getHiringDate()));
            preparedStatement.setString(7, employeeToUpdate.getMatricule());
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                return employeeToUpdate;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Employee> searchEmployee(String searchValue) {
        Connection conn = db.getConnection();
        String searchQuery = "SELECT * FROM employee WHERE " +
                "name ILIKE ? OR " +
                "prenoun ILIKE ? OR " +
                "email ILIKE ? OR " +
                "phone ILIKE ? OR " +
                "birthdate::text ILIKE ? OR " +
                "hiringdate::text ILIKE ?";

        List<Employee> foundEmployees = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(searchQuery);

            for (int i = 1; i <= 4; i++) {
                preparedStatement.setString(i, "%" + searchValue + "%");
            }

            preparedStatement.setString(5, "%" + searchValue + "%");
            preparedStatement.setString(6, "%" + searchValue + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setMatricule(resultSet.getString("matricule"));
                employee.setName(resultSet.getString("name"));
                employee.setPrenoun(resultSet.getString("prenoun"));
                employee.setEmail(resultSet.getString("email"));
                employee.setPhone(resultSet.getString("phone"));
                employee.setBirthDate(resultSet.getDate("birthdate").toLocalDate());
                employee.setHiringDate(resultSet.getDate("hiringdate").toLocalDate());

                foundEmployees.add(employee);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foundEmployees;
    }



}


