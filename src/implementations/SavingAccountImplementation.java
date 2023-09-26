package implementations;
import interfaces.EmployeeInterface;
import interfaces.SavingAccountInterface;
import helper.DatabaseConnection;
import dto.Employee;
import dto.SavingAccount;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Random;

public class SavingAccountImplementation implements SavingAccountInterface {
    public  SavingAccount add(SavingAccount savingaccount){
        LocalDate currentDate = LocalDate.now();
        savingaccount.setCreationDate(currentDate);
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            // Start a transaction
            connection.setAutoCommit(false);

            // First, insert data into the 'account' table and retrieve the generated 'number'
            PreparedStatement accountStatement = connection.prepareStatement(
                    "INSERT INTO account (balance, creationDate, accountstatus) VALUES (?, ?, ?) RETURNING number"
            );

            // Set the values for the 'account' table
            accountStatement.setDouble(1, savingaccount.getBalance());
            accountStatement.setDate(2, java.sql.Date.valueOf(savingaccount.getCreationDate()));
            accountStatement.setString(3, savingaccount.getStatus().name());

            ResultSet resultSet = accountStatement.executeQuery();
            if (resultSet.next()) {
                // Retrieve the generated 'number'
                int generatedNumber = resultSet.getInt("number");
                savingaccount.setNumber(generatedNumber);
                // Next, insert data into the 'currentaccount' table
                PreparedStatement currentAccountStatement = connection.prepareStatement(
                        "INSERT INTO savingaccount (number, interestrate) VALUES (?, ?)"
                );

                // Set the values for the 'currentaccount' table
                currentAccountStatement.setInt(1, generatedNumber);
                currentAccountStatement.setDouble(2, savingaccount.getInterestRate());

                int affectedRows = currentAccountStatement.executeUpdate();
                if (affectedRows == 1) {
                    // Commit the transaction
                    connection.commit();
                    return savingaccount;
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
