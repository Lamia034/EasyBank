package implementations;

import dto.Operation;
import interfaces.OperationInterface;

import java.sql.*;
import java.util.Optional;

import interfaces.EmployeeInterface;
import helper.DatabaseConnection;
import dto.Employee;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;
public class OperationImplementation implements OperationInterface {
    private DatabaseConnection db;

    public OperationImplementation() {
        db = DatabaseConnection.getInstance();
    }
    @Override
    public Optional<Operation> add(Operation operation) {

        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);
            LocalDate currentDate = LocalDate.now();
            operation.setCreationDate(currentDate);
            // Insert the operation into the database
            String insertQuery = "INSERT INTO operation (creationDate, montant, typeOperation, employeeMatricule, accountNumber) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertStatement.setDate(1, java.sql.Date.valueOf(operation.getCreationDate()));
                insertStatement.setFloat(2, operation.getMontant());
                insertStatement.setString(3, operation.getType().name());
                insertStatement.setString(4, operation.getEmployee().getMatricule());
                insertStatement.setInt(5, operation.getAccount().getNumber());

                int rowsInserted = insertStatement.executeUpdate();

                if (rowsInserted > 0) {
                    ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int generatedOperationNumber = generatedKeys.getInt(1);
                        operation.setNumber(generatedOperationNumber);
                        conn.commit();
                        return Optional.of(operation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<Boolean> deleteOperationByNumber(int operationNumber) {
        try (Connection conn = db.getConnection()) {
            String query = "DELETE FROM operation WHERE number = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, operationNumber);
                int rowsDeleted = preparedStatement.executeUpdate();
                return Optional.of(rowsDeleted > 0); // Return Optional<Boolean>
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty(); // Return empty Optional in case of any exception
        }
    }


}
