package implementations;
import dto.*;
import interfaces.AccountInterface;
import helper.DatabaseConnection;

import java.time.LocalDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountImplementation implements AccountInterface {
    private DatabaseConnection db;

    public AccountImplementation() {
        db = DatabaseConnection.getInstance();
    }

    @Override
    public CurrentAccount addcurrent(String employeeMatricule,Integer clientCode,CurrentAccount currentaccount){
        LocalDate currentDate = LocalDate.now();
        currentaccount.setCreationDate(currentDate);
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            // Start a transaction
            connection.setAutoCommit(false);

            // First, insert data into the 'account' table and retrieve the generated 'number'
            PreparedStatement accountStatement = connection.prepareStatement(
                    "INSERT INTO account (balance, creationDate, accountstatus , matricule , code) VALUES (?, ?, ?, ? ,?) RETURNING number"
            );

            // Set the values for the 'account' table
            accountStatement.setDouble(1, currentaccount.getBalance());
            accountStatement.setDate(2, java.sql.Date.valueOf(currentaccount.getCreationDate()));
            accountStatement.setString(3, currentaccount.getStatus().name());
            accountStatement.setString(4, currentaccount.getEmployee().getMatricule());
            accountStatement.setInt(5, currentaccount.getClient().getCode());

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

    @Override
    public SavingAccount addsaving(String employeeMatricule,Integer clientCode,SavingAccount savingaccount) {
        LocalDate currentDate = LocalDate.now();
        savingaccount.setCreationDate(currentDate);
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            // Start a transaction
            connection.setAutoCommit(false);

            // First, insert data into the 'account' table and retrieve the generated 'number'
            PreparedStatement accountStatement = connection.prepareStatement(
                    "INSERT INTO account (balance, creationDate, accountstatus , matricule , code) VALUES (?, ?, ? , ? , ?) RETURNING number"
            );

            // Set the values for the 'account' table
            accountStatement.setDouble(1, savingaccount.getBalance());
            accountStatement.setDate(2, java.sql.Date.valueOf(savingaccount.getCreationDate()));
            accountStatement.setString(3, savingaccount.getStatus().name());
            accountStatement.setString(4, savingaccount.getEmployee().getMatricule());
            accountStatement.setInt(5, savingaccount.getClient().getCode());

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

    @Override
    public boolean deleteByNumber(Integer deleteNumber) {
        Connection conn = db.getConnection();


        String deleteAccountSQL = "DELETE FROM account WHERE number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteAccountSQL)) {
            pstmt.setInt(1, deleteNumber);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }
    @Override
    public List<Account> searchByCode(Integer searchCode2) {
        List<Account> accounts = new ArrayList<>();

        try {
            Connection conn = db.getConnection();

            // Step 1: Query the 'account' table to get the account numbers associated with the client code
            String accountQuery = "SELECT * FROM account WHERE code = ?";

            try (PreparedStatement accountStatement = conn.prepareStatement(accountQuery)) {
                accountStatement.setInt(1, searchCode2);
                ResultSet accountResultSet = accountStatement.executeQuery();

                while (accountResultSet.next()) {
                    int accountNumber = accountResultSet.getInt("number");
                    float balance = accountResultSet.getFloat("balance");
                    String matricule = accountResultSet.getString("matricule");
                    int code = accountResultSet.getInt("code");
                    LocalDate creationdate = accountResultSet.getDate("creationdate").toLocalDate();
                    String status = accountResultSet.getString("accountstatus");

                    // Step 2: Check if the account is in 'currentaccount'
                    String currentAccountQuery = "SELECT * FROM currentaccount WHERE number = ?";
                    try (PreparedStatement currentAccountStatement = conn.prepareStatement(currentAccountQuery)) {
                        currentAccountStatement.setInt(1, accountNumber);
                        ResultSet currentAccountResultSet = currentAccountStatement.executeQuery();

                        if (currentAccountResultSet.next()) {
                            // Create a CurrentAccount object and add it to the list

                            CurrentAccount currentAccount = new CurrentAccount();
                            float overdraft = currentAccountResultSet.getFloat("overdraft");

                            // Set values for the CurrentAccount
                            currentAccount.setNumber(accountNumber);
                            currentAccount.setBalance(balance);
                            currentAccount.setCreationDate(creationdate);
                            currentAccount.setStatus(Account.AccountStatus.valueOf(status));
                            currentAccount.setOverdraft(overdraft);
                            Employee employee = new Employee();
                            employee.setMatricule(matricule);
                            currentAccount.setEmployee(employee);
                            currentAccount.getEmployee().setMatricule(matricule);
                            Client client = new Client();
                            client.setCode(code);
                            currentAccount.setClient(client);
                            currentAccount.getClient().setCode(code);

                            accounts.add(currentAccount);
                        } else {
                            // Step 3: If not in 'currentaccount', check in 'savingaccount'
                            String savingAccountQuery = "SELECT * FROM savingaccount WHERE number = ?";
                            try (PreparedStatement savingAccountStatement = conn.prepareStatement(savingAccountQuery)) {
                                savingAccountStatement.setInt(1, accountNumber);
                                ResultSet savingAccountResultSet = savingAccountStatement.executeQuery();

                                if (savingAccountResultSet.next()) {
                                    // Create a SavingAccount object and add it to the list
                                    SavingAccount savingAccount = new SavingAccount();
                                    float interestRate = savingAccountResultSet.getFloat("interestrate");

                                    // Set values for the SavingAccount
                                    savingAccount.setNumber(accountNumber);
                                    savingAccount.setBalance(balance);
                                    savingAccount.setCreationDate(creationdate);
                                    savingAccount.setStatus(Account.AccountStatus.valueOf(status));
                                    Employee employee = new Employee();
                                    employee.setMatricule(matricule);
                                    savingAccount.setEmployee(employee);
                                    savingAccount.getEmployee().setMatricule(matricule);
                                    Client client = new Client();
                                    client.setCode(code);
                                    savingAccount.setClient(client);
                                    savingAccount.getClient().setCode(code);

                                    savingAccount.setInterestRate(interestRate);

                                    accounts.add(savingAccount);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }





}