package implementations;

import dto.*;
import interfaces.OperationInterface;

import java.sql.*;
import java.util.Optional;

import interfaces.EmployeeInterface;
import helper.DatabaseConnection;

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
@Override
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

    @Override
    public Optional<Operation> getOperationByNumber(int operationNumber) {
        try (Connection conn = db.getConnection()) {
            String operationQuery = "SELECT * FROM operation WHERE number = ?";
            try (PreparedStatement operationStatement = conn.prepareStatement(operationQuery)) {
                operationStatement.setInt(1, operationNumber);
                ResultSet resultSet = operationStatement.executeQuery();

                if (resultSet.next()) {
                    Operation operation = new Operation();
                    operation.setNumber(resultSet.getInt("number"));
                    operation.setCreationDate(resultSet.getDate("creationDate").toLocalDate());
                    operation.setMontant(resultSet.getFloat("montant"));
                    operation.setType(Operation.typeOperation.valueOf(resultSet.getString("typeOperation")));

                    Employee employee = new Employee();
                    employee.setMatricule(resultSet.getString("employeematricule"));
                    operation.setEmployee(employee);

                    int accountNumber = resultSet.getInt("accountNumber");
                    if (isInCurrentAccount(accountNumber)) {
                        CurrentAccount currentAccount = new CurrentAccount();
                        currentAccount.setNumber(accountNumber);
                        operation.setAccount(currentAccount);
                    } else if (isInSavingAccount(accountNumber)) {
                        SavingAccount savingAccount = new SavingAccount();
                        savingAccount.setNumber(accountNumber);
                        operation.setAccount(savingAccount);
                    }

                    return Optional.of(operation);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) { // Catch the SQLException from getConnection()
            e.printStackTrace();
        }
        return Optional.empty();
    }



    private boolean isInSavingAccount(int accountNumber) {
        try (Connection conn = db.getConnection()) {
            String query = "SELECT 1 FROM savingaccount WHERE number = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, accountNumber);
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isInCurrentAccount(int accountNumber) {
        try (Connection conn = db.getConnection()) {
            String query = "SELECT 1 FROM currentaccount WHERE number = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setInt(1, accountNumber);
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
