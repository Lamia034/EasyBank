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
import java.util.Optional;

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

            connection.setAutoCommit(false);

            PreparedStatement accountStatement = connection.prepareStatement(
                    "INSERT INTO account (balance, creationDate, accountstatus , matricule , code) VALUES (?, ?, ?, ? ,?) RETURNING number"
            );

            accountStatement.setDouble(1, currentaccount.getBalance());
            accountStatement.setDate(2, java.sql.Date.valueOf(currentaccount.getCreationDate()));
            accountStatement.setString(3, currentaccount.getStatus().name());
            accountStatement.setString(4, currentaccount.getEmployee().getMatricule());
            accountStatement.setInt(5, currentaccount.getClient().getCode());

            ResultSet resultSet = accountStatement.executeQuery();
            if (resultSet.next()) {

                int generatedNumber = resultSet.getInt("number");
                currentaccount.setNumber(generatedNumber);

                PreparedStatement currentAccountStatement = connection.prepareStatement(
                        "INSERT INTO currentaccount (number, overdraft) VALUES (?, ?)"
                );

                currentAccountStatement.setInt(1, generatedNumber);
                currentAccountStatement.setDouble(2, currentaccount.getOverdraft());

                int affectedRows = currentAccountStatement.executeUpdate();
                if (affectedRows == 1) {

                    connection.commit();
                    return currentaccount;
                }
            }


            connection.rollback();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {

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

            connection.setAutoCommit(false);

            PreparedStatement accountStatement = connection.prepareStatement(
                    "INSERT INTO account (balance, creationDate, accountstatus , matricule , code) VALUES (?, ?, ? , ? , ?) RETURNING number"
            );

            accountStatement.setDouble(1, savingaccount.getBalance());
            accountStatement.setDate(2, java.sql.Date.valueOf(savingaccount.getCreationDate()));
            accountStatement.setString(3, savingaccount.getStatus().name());
            accountStatement.setString(4, savingaccount.getEmployee().getMatricule());
            accountStatement.setInt(5, savingaccount.getClient().getCode());

            ResultSet resultSet = accountStatement.executeQuery();
            if (resultSet.next()) {

                int generatedNumber = resultSet.getInt("number");
                savingaccount.setNumber(generatedNumber);
                PreparedStatement savingAccountStatement = connection.prepareStatement(
                        "INSERT INTO savingaccount (number, interestrate) VALUES (?, ?)"
                );

                savingAccountStatement.setInt(1, generatedNumber);
                savingAccountStatement.setDouble(2, savingaccount.getInterestRate());

                int affectedRows = savingAccountStatement.executeUpdate();
                if (affectedRows == 1) {
                    connection.commit();
                    return savingaccount;
                }
            }
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

    @Override
    public List<Optional<Account>> getAllAccounts() {
        List<Optional<Account>> accounts = new ArrayList<>();

        try (Connection conn = db.getConnection()) {
            String query = "SELECT a.number, a.balance, a.creationdate, a.accountstatus, a.matricule, a.code, ca.overdraft ,sa.interestrate " +
                    "FROM account a " +
                    "LEFT JOIN savingaccount sa ON a.number = sa.number " +
                    "LEFT JOIN currentaccount ca ON a.number = ca.number";


            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int accountNumber = resultSet.getInt("number");
                    float balance = resultSet.getFloat("balance");
                    LocalDate creationDate = resultSet.getDate("creationdate").toLocalDate();
                    Account.AccountStatus status = Account.AccountStatus.valueOf(resultSet.getString("accountstatus"));
                    String matricule = resultSet.getString("matricule");
                    int code = resultSet.getInt("code");

                    Optional<Account> optionalAccount = Optional.empty();

                    // Now, you can check if this account exists in the SavingAccount or CurrentAccount table
                    // and create the appropriate object accordingly
                    if (isInSavingAccount(accountNumber)) {
                        SavingAccount savingAccount = new SavingAccount();
                        savingAccount.setNumber(accountNumber);
                        savingAccount.setBalance(balance);
                        savingAccount.setCreationDate(creationDate);
                        savingAccount.setStatus(status);
                        Employee employee = new Employee();
                        employee.setMatricule(matricule);
                        savingAccount.setEmployee(employee);
                        Client client = new Client();
                        client.setCode(code);
                        savingAccount.setClient(client);

                        float interestRate = resultSet.getFloat("interestrate");
                        savingAccount.setInterestRate(interestRate);

                        optionalAccount = Optional.of(savingAccount);
                    } else if (isInCurrentAccount(accountNumber)) {
                        CurrentAccount currentAccount = new CurrentAccount();
                        currentAccount.setNumber(accountNumber);
                        currentAccount.setBalance(balance);
                        currentAccount.setCreationDate(creationDate);
                        currentAccount.setStatus(status);
                        Employee employee = new Employee();
                        employee.setMatricule(matricule);
                        currentAccount.setEmployee(employee);
                        Client client = new Client();
                        client.setCode(code);
                        currentAccount.setClient(client);

                        float overdraft = resultSet.getFloat("overdraft");
                        currentAccount.setOverdraft(overdraft);

                        optionalAccount = Optional.of(currentAccount);
                    }

                    accounts.add(optionalAccount);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Optional<Account> updateAccount(Account account) {
        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false);

            String accountQuery = "UPDATE account SET balance = ?, matricule = ?, code = ?, creationdate = ?, accountstatus = ? WHERE number = ?";
            try (PreparedStatement accountStatement = conn.prepareStatement(accountQuery)) {
                accountStatement.setFloat(1, account.getBalance());
                accountStatement.setString(2, account.getEmployee().getMatricule());
                accountStatement.setInt(3, account.getClient().getCode());
                accountStatement.setDate(4, java.sql.Date.valueOf(account.getCreationDate()));
                accountStatement.setString(5, account.getStatus().name());
                accountStatement.setInt(6, account.getNumber());

                int accountRowsUpdated = accountStatement.executeUpdate();

                if (accountRowsUpdated > 0) {
                    if (account instanceof CurrentAccount) {
                        String currentAccountQuery = "UPDATE currentaccount SET overdraft = ? WHERE number = ?";
                        try (PreparedStatement currentAccountStatement = conn.prepareStatement(currentAccountQuery)) {
                            CurrentAccount currentAccount = (CurrentAccount) account;
                            currentAccountStatement.setFloat(1, currentAccount.getOverdraft());
                            currentAccountStatement.setInt(2, account.getNumber());

                            int currentAccountRowsUpdated = currentAccountStatement.executeUpdate();


                            if (currentAccountRowsUpdated > 0) {
                                conn.commit();
                                return Optional.of(account);
                            }
                        }
                    } else if (account instanceof SavingAccount) {
                        String savingAccountQuery = "UPDATE savingaccount SET interestrate = ? WHERE number = ?";
                        try (PreparedStatement savingAccountStatement = conn.prepareStatement(savingAccountQuery)) {
                            SavingAccount savingAccount = (SavingAccount) account;
                            savingAccountStatement.setFloat(1, savingAccount.getInterestRate());
                            savingAccountStatement.setInt(2, account.getNumber());

                            int savingAccountRowsUpdated = savingAccountStatement.executeUpdate();


                            if (savingAccountRowsUpdated > 0) {
                                conn.commit();
                                return Optional.of(account);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
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