package implementations;
import interfaces.EmployeeInterface;
import interfaces.CurrentAccountInterface;
import helper.DatabaseConnection;
import dto.Employee;
import dto.Account;
import dto.CurrentAccount;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Random;

public class CurrentAccountImplementation implements CurrentAccountInterface{
    private DatabaseConnection db;

    public CurrentAccountImplementation() {
        db = DatabaseConnection.getInstance();
    }
    @Override
    public CurrentAccount add(CurrentAccount currentaccount) {
        LocalDate currentDate = LocalDate.now();
        currentaccount.setCreationDate(currentDate);
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            // Start a transaction
            connection.setAutoCommit(false);

            // First, insert data into the 'account' table and retrieve the generated 'number'
            PreparedStatement accountStatement = connection.prepareStatement(
                    "INSERT INTO account (balance, creationDate, accountstatus) VALUES (?, ?, ?) RETURNING number"
            );

            // Set the values for the 'account' table
            accountStatement.setDouble(1, currentaccount.getBalance());
            accountStatement.setDate(2, java.sql.Date.valueOf(currentaccount.getCreationDate()));
            accountStatement.setString(3, currentaccount.getStatus().name());

            ResultSet resultSet = accountStatement.executeQuery();
            if (resultSet.next()) {
                // Retrieve the generated 'number'
                int generatedNumber = resultSet.getInt("number");
                currentaccount.setNumber(generatedNumber);
                // Next, insert data into the 'currentaccount' table
                PreparedStatement currentAccountStatement = connection.prepareStatement(
                        "INSERT INTO currentaccount (number, overdraft) VALUES (?, ?)"
                );

                // Set the values for the 'currentaccount' table
                currentAccountStatement.setInt(1, generatedNumber);
                currentAccountStatement.setDouble(2, currentaccount.getOverdraft());

                int affectedRows = currentAccountStatement.executeUpdate();
                if (affectedRows == 1) {
                    // Commit the transaction
                    connection.commit();
                    return currentaccount;
                }
            }

            // If any insert operation fails, rollback the transaction
            connection.rollback();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Exception occurred
        } finally {
            // Restore auto-commit mode
            try {
                Connection connection = DatabaseConnection.getInstance().getConnection();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
