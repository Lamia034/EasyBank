package services;

import dto.*;
import implementations.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import dto.*;
import helper.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import implementations.*;

import java.time.format.DateTimeFormatter;


import java.util.stream.Collectors;

public class Services {
    static Employee employee = new Employee();
    static EmployeeImplementation employeeI = new EmployeeImplementation();
    static Client client = new Client();
    static ClientImplementation clientI = new ClientImplementation();

    static CurrentAccount currentaccount = new CurrentAccount();
    static SavingAccount savingaccount = new SavingAccount();
    static AccountImplementation accountI = new AccountImplementation();
    static Operation operation = new Operation();
    static OperationImplementation operationI = new OperationImplementation();
    static Mission mission = new Mission();
    static MissionImplementation missionI = new MissionImplementation();
    static Affectation affectation = new Affectation();
    static AffectationImplementation affectationI = new AffectationImplementation();

    Scanner scanner = new Scanner(System.in);
////////////////////////////employee services//////////////////////////////
    public void addemployee(){


        System.out.print("Enter employee name: ");
        employee.setName(scanner.nextLine());

        System.out.print("Enter employee prenoun: ");
        employee.setPrenoun(scanner.nextLine());

        System.out.print("Enter employee birth date (dd-MM-yyyy): ");
        String birthDateStr = scanner.nextLine();
        LocalDate birthDate = null;
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            birthDate = LocalDate.parse(birthDateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please use dd-MM-yyyy.");

        }
        employee.setBirthDate(birthDate);

        System.out.print("Enter employee Phone: ");
        employee.setPhone(scanner.nextLine());

        System.out.print("Enter employee email: ");
        employee.setEmail(scanner.nextLine());

        if (employeeI.add(employee) != null) {
            System.out.println("Employee added successfully!:");
            System.out.println("name: " + employee.getName() + ",prenoun: " + employee.getPrenoun() + ",Birth date: " + employee.getBirthDate() + " phone: " + employee.getPhone() + ",Hiring date: " + employee.getHiringDate() + " matricule: " + employee.getMatricule() + " email: " + employee.getEmail());
        } else {
            System.out.println("Failed to add the employee.");
        }
    }

    public void findemployeebymatricul(){
        System.out.print("Enter employee matricule to search: ");
        String searchMatricule = scanner.nextLine();

        Employee foundEmployee = employeeI.searchByMatricule(searchMatricule);

        if (foundEmployee != null) {
            System.out.println("Found employee:");
            System.out.println("Matricule: " + foundEmployee.getMatricule());
            System.out.println("Name: " + foundEmployee.getName());
            System.out.println("Prenoun: " + foundEmployee.getPrenoun());
            System.out.println("birth date: " + foundEmployee.getBirthDate());
            System.out.println("Email: " + foundEmployee.getEmail());
            System.out.println("Phone: " + foundEmployee.getPhone());
            System.out.println("hiring date: " + foundEmployee.getHiringDate());
        } else {
            System.out.println("Employee not found with Matricule: " + searchMatricule);
        }
    }

    public void deleteemployee(){
        System.out.print("Enter employee matricule to delete: ");
        String deleteMatricule = scanner.nextLine();

        boolean deleted = employeeI.deleteByMatricule(deleteMatricule);

        if (deleted) {
            System.out.println("Employee with Matricule " + deleteMatricule + " deleted successfully.");
        } else {
            System.out.println("Employee with Matricule " + deleteMatricule + " not found or deletion failed.");
        }
    }

    public void listallemployees(){
        List<Employee> allEmployees = employeeI.getAllEmployees();

        if (allEmployees != null && !allEmployees.isEmpty()) {
            System.out.println("All Employees:");

            for (Employee employee : allEmployees) {
                System.out.println("Matricule: " + employee.getMatricule());
                System.out.println("Name: " + employee.getName());
                System.out.println("Prenoun: " + employee.getPrenoun());
                System.out.println("email: " + employee.getEmail());
                System.out.println("Phone: " + employee.getPhone());
                System.out.println("birth date: " + employee.getBirthDate());
                System.out.println("hiring Date: " + employee.getHiringDate());

                System.out.println();
            }
        } else {
            System.out.println("No employee found.");
        }
    }
    public void updateemployee(){
        System.out.print("Enter the matricule to update employee informatiosn: ");
        String updateMatricule = scanner.nextLine();
        try {

            Employee employeeToUpdate = employeeI.searchByMatricule(updateMatricule);

            if (employeeToUpdate != null) {
                System.out.println("employee found:");
                System.out.println("Matricule: " + employeeToUpdate.getMatricule());
                System.out.println("Name: " + employeeToUpdate.getName());
                System.out.println("prenoun: " + employeeToUpdate.getPrenoun());
                System.out.println("email: " + employeeToUpdate.getEmail());
                System.out.println("phone: " + employeeToUpdate.getPhone());
                System.out.println("birth date: " + employeeToUpdate.getBirthDate());
                System.out.println("hiring date: " + employeeToUpdate.getHiringDate());
                System.out.println("Enter new employee information (or leave blank to keep existing information):");

                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                if (!newName.isEmpty()) {
                    employeeToUpdate.setName(newName);
                }

                System.out.print("Enter new prenoun: ");
                String newPrenoun = scanner.nextLine();
                if (!newPrenoun.isEmpty()) {
                    employeeToUpdate.setPrenoun(newPrenoun);
                }

                System.out.print("Enter new email: ");
                String newEmail = scanner.nextLine();
                if (!newEmail.isEmpty()) {
                    employeeToUpdate.setEmail(newEmail);
                }

                System.out.print("Enter new phone: ");
                String newPhone = scanner.nextLine();
                if (!newPhone.isEmpty()) {
                    employeeToUpdate.setPhone(newPhone);
                }

                System.out.print("Enter new birth date (yyyy-MM-dd): ");
                String newBirthDateStr = scanner.nextLine();
                if (!newBirthDateStr.isEmpty()) {
                    LocalDate newBirthDate = LocalDate.parse(newBirthDateStr);
                    employeeToUpdate.setBirthDate(newBirthDate);
                }

                System.out.print("Enter new hiring date (yyyy-MM-dd): ");
                String newHiringDateStr = scanner.nextLine();
                if (!newHiringDateStr.isEmpty()) {
                    LocalDate newHiringDate = LocalDate.parse(newHiringDateStr);
                    employeeToUpdate.setHiringDate(newHiringDate);
                }


                if (employeeI.update(employeeToUpdate) != null) {
                    System.out.println("employee updated successfully! with name:" + employeeToUpdate.getName() + " , prenoun " + employeeToUpdate.getPrenoun() + " , email " + employeeToUpdate.getEmail() + " , phone " + employeeToUpdate.getPhone() + " , birth date " + employeeToUpdate.getBirthDate() + " , hiring date " + employeeToUpdate.getHiringDate());
                } else {
                    System.out.println("Failed to update the employee.");
                }
            } else {
                System.out.println("employee not found with Number: " + updateMatricule);
            }


        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid matricule.");
        }
    }


    public void findemployeebyattributs(){
        System.out.print("Enter any of the employee's information to search: ");
        String searchValue = scanner.nextLine();

        List<Employee> foundemployees = employeeI.searchEmployee(searchValue);

        if (!foundemployees.isEmpty()) {
            System.out.println("Found employee(s):");
            for (Employee foundemployee : foundemployees) {
                System.out.println("Matricule: " + foundemployee.getMatricule());
                System.out.println("Name: " + foundemployee.getName());
                System.out.println("Prenoun: " + foundemployee.getPrenoun());
                System.out.println("Birth date: " + foundemployee.getBirthDate());
                System.out.println("Email: " + foundemployee.getEmail());
                System.out.println("Phone: " + foundemployee.getPhone());
                System.out.println("Hiring date: " + foundemployee.getHiringDate());
                System.out.println();
            }
        } else {
            System.out.println("No employee found with the provided information: " + searchValue);
        }
    }

    /////////////////////////////client services /////////////////////////////////
    public void addclient(){
        System.out.print("Enter client name: ");
        client.setName(scanner.nextLine());

        System.out.print("Enter client prenoun: ");
        client.setPrenoun(scanner.nextLine());

        System.out.print("Enter client birth date (dd-MM-yyyy): ");
        String birthDateStrr = scanner.nextLine();
        LocalDate clientBirthDate = null;

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            clientBirthDate = LocalDate.parse(birthDateStrr, dateFormatter);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please use dd-MM-yyyy.");

        }

        client.setBirthDate(clientBirthDate);

        System.out.print("Enter client Phone: ");
        client.setPhone(scanner.nextLine());

        System.out.print("Enter adresse : ");
        client.setAdresse(scanner.nextLine());

        Optional<Client> addResult = clientI.add(client);
        if (addResult.isPresent()) {
            Client addedClient = addResult.get();
            System.out.println("Client added successfully!:");
            System.out.println("name: " + addedClient.getName() + ",prenoun: " + addedClient.getPrenoun() + ",Birth date: " + addedClient.getBirthDate() + " phone: " + addedClient.getPhone() + " code: " + addedClient.getCode() + " adresse: " + addedClient.getAdresse());
        } else {
            System.out.println("Failed to add the client.");
        }
    }
    public void findclient(){
        System.out.print("Enter client code to search: ");
        Integer searchCode = scanner.nextInt();
        scanner.nextLine();

        Client foundClient = clientI.searchByCode(searchCode);

        if (foundClient != null) {
            System.out.println("Found client:");
            System.out.println("Code: " + foundClient.getCode());
            System.out.println("Name: " + foundClient.getName());
            System.out.println("Prenoun: " + foundClient.getPrenoun());
            System.out.println("birth date: " + foundClient.getBirthDate());
            System.out.println("Adresse: " + foundClient.getAdresse());
            System.out.println("Phone: " + foundClient.getPhone());
        } else {
            System.out.println("Client not found with Code: " + searchCode);
        }
    }

    public void deleteclient(){
        System.out.print("Enter client code to delete: ");
        Integer deleteCode = scanner.nextInt();
        scanner.nextLine();

        boolean deletedc = clientI.deleteByCode(deleteCode);

        if (deletedc) {
            System.out.println("Client with Code " + deleteCode + " deleted successfully.");
        } else {
            System.out.println("Client with Code " + deleteCode + " not found or deletion failed.");
        }
    }

    public void listallclients(){
        List<Client> allClients = clientI.getAllClients();

        if (allClients != null && !allClients.isEmpty()) {
            System.out.println("All Clients:");

            for (Client client : allClients) {
                System.out.println("Code: " + client.getCode());
                System.out.println("Name: " + client.getName());
                System.out.println("Prenoun: " + client.getPrenoun());
                System.out.println("adresse: " + client.getAdresse());
                System.out.println("Phone: " + client.getPhone());
                System.out.println("birth date: " + client.getBirthDate());

                System.out.println();
            }
        } else {
            System.out.println("No client found.");
        }
    }
    public void updateclient(){
        System.out.print("Enter the code to update client informations: ");
        Integer updateCode = Integer.valueOf(scanner.next());
        scanner.nextLine();
        try {

            Client clientToUpdate = clientI.searchByCode(updateCode);

            if (clientToUpdate != null) {
                System.out.println("client found:");
                System.out.println("Code: " + clientToUpdate.getCode());
                System.out.println("Name: " + clientToUpdate.getName());
                System.out.println("prenoun: " + clientToUpdate.getPrenoun());
                System.out.println("adresse: " + clientToUpdate.getAdresse());
                System.out.println("phone: " + clientToUpdate.getPhone());
                System.out.println("birth date: " + clientToUpdate.getBirthDate());
                System.out.println("Enter new client information (or leave blank to keep existing information):");

                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                if (!newName.isEmpty()) {
                    clientToUpdate.setName(newName);
                }

                System.out.print("Enter new prenoun: ");
                String newPrenoun = scanner.nextLine();
                if (!newPrenoun.isEmpty()) {
                    clientToUpdate.setPrenoun(newPrenoun);
                }

                System.out.print("Enter new adresse: ");
                String newAdresse = scanner.nextLine();
                if (!newAdresse.isEmpty()) {
                    clientToUpdate.setAdresse(newAdresse);
                }

                System.out.print("Enter new phone: ");
                String newPhone = scanner.nextLine();
                if (!newPhone.isEmpty()) {
                    clientToUpdate.setPhone(newPhone);
                }

                System.out.print("Enter new birth date (yyyy-MM-dd): ");
                String newBirthDateStr = scanner.nextLine();
                if (!newBirthDateStr.isEmpty()) {
                    LocalDate newBirthDate = LocalDate.parse(newBirthDateStr);
                    clientToUpdate.setBirthDate(newBirthDate);
                }

                if (clientI.update(clientToUpdate) != null) {
                    System.out.println("client updated successfully! with name:" + clientToUpdate.getName() + " , prenoun " + clientToUpdate.getPrenoun() + " , adresse " + clientToUpdate.getAdresse() + " , phone " + clientToUpdate.getPhone() + " , birth date " + clientToUpdate.getBirthDate());
                } else {
                    System.out.println("Failed to update the client.");
                }
            } else {
                System.out.println("client not found with Number: " + updateCode);
            }


        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid code.");
        }
    }

    public void findclientbyattributs(){
        System.out.print("Enter any of the client's information to search: ");
        String searchValue2 = scanner.nextLine();

        List<Client> foundclients = clientI.searchClient(searchValue2);

        if (!foundclients.isEmpty()) {
            System.out.println("Found client(s):");
            for (Client foundclient : foundclients) {
                System.out.println("code: " + foundclient.getCode());
                System.out.println("Name: " + foundclient.getName());
                System.out.println("Prenoun: " + foundclient.getPrenoun());
                System.out.println("Birth date: " + foundclient.getBirthDate());
                System.out.println("adresse: " + foundclient.getAdresse());
                System.out.println("Phone: " + foundclient.getPhone());
                System.out.println();
            }
        } else {
            System.out.println("No client found with the provided information: " + searchValue2);
        }
    }
/////////////////////////////////accounts service//////////////////////////
    public void addcurrentaccount(){
        System.out.print("Enter your matricule: ");
        String employeeMatricule = scanner.nextLine();
        currentaccount.setEmployee(employee);
        currentaccount.getEmployee().setMatricule(employeeMatricule);

        System.out.print("Enter client code: ");
        Integer clientCode = scanner.nextInt();
        currentaccount.setClient(client);
        currentaccount.getClient().setCode(clientCode);
        scanner.nextLine();

        System.out.print("Enter the balance: ");
        currentaccount.setBalance(scanner.nextFloat());
        scanner.nextLine();

        System.out.println("Choose the account's status:");
        System.out.println("1. Actif");
        System.out.println("2. Inactif");

        int choiceS = scanner.nextInt();
        scanner.nextLine();


        switch (choiceS) {
            case 1:
                currentaccount.setStatus(CurrentAccount.AccountStatus.ACTIF);
                break;
            case 2:
                currentaccount.setStatus(CurrentAccount.AccountStatus.INACTIF);
                break;
            default:
                System.out.println("Invalid choice. Setting status to ACTIF by default.");
                currentaccount.setStatus(CurrentAccount.AccountStatus.ACTIF);
                break;
        }

        System.out.print("Enter the Overdraft: ");
        currentaccount.setOverdraft(scanner.nextFloat());
        scanner.nextLine();

        if (accountI.addcurrent(employeeMatricule, clientCode, currentaccount) != null) {
            System.out.println("Current account added successfully!:");
            System.out.println("account number: " + currentaccount.getNumber() + ",balance: " + currentaccount.getBalance() + ",Creation date: " + currentaccount.getCreationDate() + " status: " + currentaccount.getStatus() + ", Overdraft: " + currentaccount.getOverdraft());
        } else {
            System.out.println("Failed to add the account.");
        }
    }
    public void addsavingaccount(){
        System.out.print("Enter your matricule: ");
        String employeeMatriculee = scanner.nextLine();
        currentaccount.setEmployee(employee);
        currentaccount.getEmployee().setMatricule(employeeMatriculee);

        System.out.print("Enter client code: ");
        Integer clientCodee = scanner.nextInt();
        currentaccount.setClient(client);
        currentaccount.getClient().setCode(clientCodee);
        scanner.nextLine();
        System.out.print("Enter the balance: ");
        savingaccount.setBalance(scanner.nextFloat());
        scanner.nextLine();

        System.out.println("Choose the account's status:");
        System.out.println("1. Actif");
        System.out.println("2. Inactif");

        int choiceSS = scanner.nextInt();
        scanner.nextLine();


        switch (choiceSS) {
            case 1:
                savingaccount.setStatus(SavingAccount.AccountStatus.ACTIF);
                break;
            case 2:
                savingaccount.setStatus(SavingAccount.AccountStatus.INACTIF);
                break;
            default:
                System.out.println("Invalid choice. Setting status to ACTIF by default.");
                savingaccount.setStatus(SavingAccount.AccountStatus.ACTIF);
                break;
        }

        System.out.print("Enter the Interest Rate: ");
        savingaccount.setInterestRate(scanner.nextFloat());
        scanner.nextLine();

        if (accountI.addsaving(employeeMatriculee, clientCodee, savingaccount) != null) {
            System.out.println("saving account added successfully!:");
            System.out.println("account number: " + savingaccount.getNumber() + ",balance: " + savingaccount.getBalance() + ",Creation date: " + savingaccount.getCreationDate() + " status: " + savingaccount.getStatus() + ", Overdraft: " + savingaccount.getInterestRate());
        } else {
            System.out.println("Failed to add the account.");
        }
    }
    public void deleteaccount(){
        System.out.print("Enter account's number to delete: ");
        Integer deleteNumber = scanner.nextInt();
        scanner.nextLine();

        boolean deletedacc = accountI.deleteByNumber(deleteNumber);

        if (deletedacc) {
            System.out.println("account with number " + deleteNumber + " deleted successfully.");
        } else {
            System.out.println("account with number " + deleteNumber + " not found or deletion failed.");
        }
    }

    public void findaccountbyclient(){
        System.out.print("Enter client code to find his account: ");
        Integer searchCode2 = scanner.nextInt();
        scanner.nextLine();

        List<Account> foundAccounts = accountI.searchByCode(searchCode2);

        if (!foundAccounts.isEmpty()) {
            System.out.println("Found account(s):");
            for (Account foundAccount : foundAccounts) {
                if (foundAccount instanceof CurrentAccount) {
                    CurrentAccount currentAccount = (CurrentAccount) foundAccount;
                    System.out.println("Current Account:");
                    System.out.println("number: " + currentAccount.getNumber());
                    System.out.println("balance: " + currentAccount.getBalance());
                    System.out.println("creation date: " + currentAccount.getCreationDate());
                    System.out.println("status: " + currentAccount.getStatus());
                    System.out.println("overdraft: " + currentAccount.getOverdraft());
                    System.out.println("matricule: " + currentAccount.getEmployee().getMatricule());
                    System.out.println("code: " + currentAccount.getClient().getCode());


                    System.out.println();
                } else {
                    SavingAccount savingAccount = (SavingAccount) foundAccount;
                    System.out.println("Other Account Type:");
                    System.out.println("number: " + savingAccount.getNumber());
                    System.out.println("balance: " + savingAccount.getBalance());
                    System.out.println("creation date: " + savingAccount.getCreationDate());
                    System.out.println("status: " + savingAccount.getStatus());
                    System.out.println("interest rate: " + savingAccount.getInterestRate());
                    System.out.println("matricule: " + savingAccount.getEmployee().getMatricule());
                    System.out.println("code: " + savingAccount.getClient().getCode());
                    System.out.println();
                }
            }
        } else {
            System.out.println("No accounts found with Code: " + searchCode2);
        }
    }
public void listallaccounts(){
    List<Optional<Account>> allAccounts = accountI.getAllAccounts();

    if (allAccounts != null && !allAccounts.isEmpty()) {
        System.out.println("All account(s):");
        for (Optional<Account> optionalAccount : allAccounts) {
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                if (account instanceof SavingAccount) {
                    SavingAccount savingAccount = (SavingAccount) account;
                    System.out.println("Saving Account:");
                    System.out.println("number: " + savingAccount.getNumber());
                    System.out.println("balance: " + savingAccount.getBalance());
                    System.out.println("creation date: " + savingAccount.getCreationDate());
                    System.out.println("status: " + savingAccount.getStatus());
                    System.out.println("interest rate: " + savingAccount.getInterestRate());
                    System.out.println("matricule: " + savingAccount.getEmployee().getMatricule());
                    System.out.println("code: " + savingAccount.getClient().getCode());
                    System.out.println();
                } else if (account instanceof CurrentAccount) {
                    CurrentAccount currentAccount = (CurrentAccount) account;
                    System.out.println("Current Account:");
                    System.out.println("number: " + currentAccount.getNumber());
                    System.out.println("balance: " + currentAccount.getBalance());
                    System.out.println("creation date: " + currentAccount.getCreationDate());
                    System.out.println("status: " + currentAccount.getStatus());
                    System.out.println("overdraft: " + currentAccount.getOverdraft());
                    System.out.println("matricule: " + currentAccount.getEmployee().getMatricule());
                    System.out.println("code: " + currentAccount.getClient().getCode());
                    System.out.println();
                }
            }
        }
    } else {
        System.out.println("No accounts found.");
    }
}
public void filteraccountsbychoosenstatus(){
    List<Optional<Account>> allAccounts2 = accountI.getAllAccounts();
    System.out.println("Choose an account status to filter by:");
    System.out.println("1. ACTIVE");
    System.out.println("2. INACTIVE");
    int choice3 = scanner.nextInt();
    scanner.nextLine();
    Account.AccountStatus desiredStatus;
    switch (choice3) {
        case 1:
            desiredStatus = Account.AccountStatus.ACTIF;
            break;
        case 2:
            desiredStatus = Account.AccountStatus.INACTIF;
            break;
        default:
            System.out.println("Invalid choice. Defaulting to ACTIVE.");
            desiredStatus = Account.AccountStatus.ACTIF;
            break;
    }

    List<Account> filteredAccounts = allAccounts2.stream()
            .filter(optionalAccount -> optionalAccount.isPresent())
            .map(Optional::get)
            .filter(account -> account.getStatus() == desiredStatus)
            .collect(Collectors.toList());


    if (!filteredAccounts.isEmpty()) {
        System.out.println("Accounts with status " + desiredStatus + ":");
        for (Account account : filteredAccounts) {
            System.out.println("Number: " + account.getNumber());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("matricule: " + account.getEmployee().getMatricule());
            System.out.println("code: " + account.getClient().getCode());

            System.out.println();
        }
    } else {
        System.out.println("no account found with this status");
    }
}
    public void filteraccountsbycreationdate() {
        List<Optional<Account>> allAccounts4 = accountI.getAllAccounts();

        System.out.print("Enter a date (yyyy-MM-dd): ");
        String inputDate = scanner.nextLine();

        LocalDate desiredDate ;
        try {
            desiredDate = LocalDate.parse(inputDate);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        List<Account> accountsByDate = allAccounts4.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(account -> account.getCreationDate().equals(desiredDate))
                .collect(Collectors.toList());

        if (!accountsByDate.isEmpty()) {
            System.out.println("Accounts created on " + desiredDate + ":");
            for (Account account : accountsByDate) {
                System.out.println("Number: " + account.getNumber());
                System.out.println("Balance: " + account.getBalance());
                System.out.println("Creation Date: " + account.getCreationDate());
                System.out.println("matricule: " + account.getEmployee().getMatricule());
                System.out.println("code: " + account.getClient().getCode());
                System.out.println();
            }
        } else {
            System.out.println("No accounts found for the specified date.");
        }
    }

public void changeaccountstatus(){
    List<Optional<Account>> allAccounts5 = accountI.getAllAccounts();
    System.out.print("Enter the account number: ");
    int accountNumberToChangeStatus = scanner.nextInt();

    Optional<Account> accountToChangeStatus = allAccounts5.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(account -> account.getNumber() == accountNumberToChangeStatus)
            .findFirst();

    if (accountToChangeStatus.isPresent()) {
        Account account = accountToChangeStatus.get();
        System.out.println("Account number: " + account.getNumber());
        System.out.println("Account status: " + account.getStatus());
        System.out.println("Choose an account status to change:");
        System.out.println("1. ACTIVE");
        System.out.println("2. INACTIVE");
        int choice4 = scanner.nextInt();
        scanner.nextLine();
        Account.AccountStatus changeAccountStatus;
        switch (choice4) {
            case 1:
                changeAccountStatus = Account.AccountStatus.ACTIF;
                break;
            case 2:
                changeAccountStatus = Account.AccountStatus.INACTIF;
                break;
            default:
                System.out.println("Invalid choice. Defaulting to ACTIVE.");
                changeAccountStatus = Account.AccountStatus.ACTIF;
                break;
        }


        account.setStatus(changeAccountStatus);

        Optional<Account> updatedAccount = accountI.updateAccount(account);

        if (updatedAccount.isPresent()) {
            System.out.println("Account status updated successfully.");
        } else {
            System.out.println("Failed to update account status.");
        }
    } else {
        System.out.println("Account not found.");
    }
}
public void updateaccountbyitsnumber(){
    List<Optional<Account>> allAccounts6 = accountI.getAllAccounts();
    System.out.println("Enter the account number:");
    int accountNumberToFind = scanner.nextInt();
    scanner.nextLine();

    Optional<Account> accountToUpdate = allAccounts6.stream()
            .filter(account -> account.isPresent() && account.get().getNumber() == accountNumberToFind)
            .map(Optional::get)
            .findFirst();

    if (accountToUpdate.isPresent()) {
        Account account = accountToUpdate.get();
        System.out.println("Account found. Current details:");
        System.out.println("Number: " + account.getNumber());
        System.out.println("Balance: " + account.getBalance());
        System.out.println("creation date: " + account.getCreationDate());
        System.out.println("Status: " + account.getStatus());
        System.out.println("Matricule: " + account.getEmployee().getMatricule());
        System.out.println("Code: " + account.getClient().getCode());
        if (account instanceof CurrentAccount) {
            CurrentAccount currentAccount = (CurrentAccount) account;
            System.out.println("overdraft: " + currentAccount.getOverdraft());
        } else if (account instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) account;
            System.out.println("interest rate: " + savingAccount.getInterestRate());
        }

        System.out.println("Enter new account information (or leave blank to keep existing information):");

        System.out.print("Enter new Balance: ");
        Float newBalance = scanner.nextFloat();
        scanner.nextLine();

        System.out.print("Enter new Status: ");
        String newStatusInput = scanner.nextLine();
        Account.AccountStatus newStatus = null;
        if (!newStatusInput.isEmpty()) {
            newStatus = Account.AccountStatus.valueOf(newStatusInput);
        }

        if (account instanceof CurrentAccount) {
            CurrentAccount currentAccount = (CurrentAccount) account;

            System.out.print("Enter new overdraft: ");
            Float newOverdraft = scanner.nextFloat();
            scanner.nextLine();

            currentAccount.setOverdraft(newOverdraft);
        } else if (account instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) account;

            System.out.print("Enter new interest rate: ");
            Float newInterestRate = scanner.nextFloat();
            scanner.nextLine();

            savingAccount.setInterestRate(newInterestRate);
        }

        if (newBalance != null) {
            account.setBalance(newBalance);
        }
        if (newStatus != null) {
            account.setStatus(newStatus);
        }

        Optional<Account> updatedAccount = accountI.updateAccount(account);

        if (updatedAccount.isPresent()) {
            Account updated = updatedAccount.get();


            System.out.println("Account updated successfully. Updated details:");
            System.out.println("Number: " + updated.getNumber());
            System.out.println("Balance: " + updated.getBalance());
            System.out.println("Status: " + updated.getStatus());
            System.out.println("Matricule: " + updated.getEmployee().getMatricule());
            System.out.println("Code: " + updated.getClient().getCode());
            if (account instanceof CurrentAccount) {
                CurrentAccount currentAccount = (CurrentAccount) account;
                System.out.println("overdraft: " + currentAccount.getOverdraft());
            } else if (account instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) account;
                System.out.println("interest rate: " + savingAccount.getInterestRate());
            }
        } else {
            System.out.println("Failed to update the account.");
        }
    } else {
        System.out.println("Account not found with the specified number.");
    }
}
/////////////////////////////operations service ///////////////////////////////////
    public void addoperation(){
        List<Optional<Account>> allAccounts7 = accountI.getAllAccounts();
        System.out.print("Enter matricule: ");
        String matricule = scanner.nextLine();

        Employee employee = new Employee();
        employee.setMatricule(matricule);

        System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter operation montant: ");
        float montant = scanner.nextFloat();
        scanner.nextLine();

        System.out.println("Enter operation type:");
        System.out.println("1. PAYMENT");
        System.out.println("2. WITHDRAW");
        int choice4 = scanner.nextInt();
        scanner.nextLine();
        Operation.typeOperation operationType;
        switch (choice4) {
            case 1:
                operationType = Operation.typeOperation.PAYEMENT;
                break;
            case 2:
                operationType = Operation.typeOperation.WITHDRAW;
                break;
            default:
                System.out.println("Invalid choice. Defaulting to PAYMENT.");
                operationType = Operation.typeOperation.PAYEMENT;
                break;
        }

        Optional<Account> accountOptional = allAccounts7.stream()
                .filter(account -> account.isPresent() && account.get().getNumber() == accountNumber)
                .map(Optional::get)
                .findFirst();

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();

            // Create a new Operation object
            Operation operation = new Operation();
            operation.setEmployee(employee);
            operation.setAccount(account);
            operation.setMontant(montant);
            operation.setType(operationType);

            if (operation.getType() == Operation.typeOperation.PAYEMENT) {
                // Add montant to the account balance
                account.setBalance(account.getBalance() + montant);
            } else if (operation.getType() == Operation.typeOperation.WITHDRAW) {
                // Subtract montant from the account balance
                account.setBalance(account.getBalance() - montant);
            }

            // Update the account balance in the database
            accountI.updateAccount(account);

            // Add the operation to the database
            Optional<Operation> addedOperation = operationI.add(operation);

            if (addedOperation.isPresent()) {
                Operation added = addedOperation.get();
                System.out.println("Operation added successfully!");
                System.out.println("Number: " + added.getNumber());
                System.out.println("Creation Date: " + added.getCreationDate());
                System.out.println("Montant: " + added.getMontant());
                System.out.println("Type: " + added.getType());
            } else {
                System.out.println("Failed to add the operation.");
            }
        } else {
            System.out.println("Account not found with the specified number.");
        }
    }
    public void deleteoperation(){
        System.out.print("Enter operation number to delete: ");
        int operationNumberToDelete = scanner.nextInt();
        scanner.nextLine();

        Optional<Boolean> deletionResult = operationI.deleteOperationByNumber(operationNumberToDelete);

        if (deletionResult.isPresent()) {
            if (deletionResult.get()) {
                System.out.println("Operation deleted successfully.");
            } else {
                System.out.println("Operation not found with the specified number.");
            }
        } else {
            System.out.println("Failed to delete the operation.");
        }
    }
    public void searchoperationbynumber(){
        System.out.print("Enter operation number to search: ");
        int operationNumberToSearch = scanner.nextInt();
        scanner.nextLine();

        Optional<Operation> searchedOperation = operationI.getOperationByNumber(operationNumberToSearch);

        if (searchedOperation.isPresent()) {
            Operation operation1 = searchedOperation.get();
            System.out.println("Operation found:");
            System.out.println("Number: " + operation1.getNumber());
            System.out.println("Creation Date: " + operation1.getCreationDate());
            System.out.println("Montant: " + operation1.getMontant());
            System.out.println("Type: " + operation1.getType());
            System.out.println("Employee Matricule: " + operation1.getEmployee().getMatricule());
            System.out.println("Account Number: " + operation1.getAccount().getNumber());
        } else {
            System.out.println("Operation not found with the specified number.");
        }
    }
    /////////////////////////missions services//////////////////////////
    public void addmission(){
        System.out.print("Enter mission name: ");
        String missionName = scanner.nextLine();

        System.out.print("Enter mission description: ");
        String missionDesc = scanner.nextLine();


        mission.setName(missionName);
        mission.setDescription(missionDesc);

        Optional<Mission> addedMission = missionI.addMission(mission);

        if (addedMission.isPresent()) {
            Mission added = addedMission.get();
            System.out.println("Mission added successfully!");
            System.out.println("Code: " + added.getCode());
            System.out.println("Name: " + added.getName());
            System.out.println("Description: " + added.getDescription());
        } else {
            System.out.println("Failed to add the mission.");
        }
    }
    public void deletemission(){
        System.out.print("Enter mission code to delete: ");
        int missionCodeToDelete = scanner.nextInt();
        scanner.nextLine();

        Optional<Boolean> deleteMissionResult = missionI.deleteMissionByCode(missionCodeToDelete);

        if (deleteMissionResult.isPresent()) {
            if (deleteMissionResult.get()) {
                System.out.println("Mission deleted successfully.");
            } else {
                System.out.println("Mission not found with the specified code.");
            }
        } else {
            System.out.println("Failed to delete the mission.");
        }
    }
    public void listallmissions(){
        List<Optional<Mission>> allMissions = missionI.getAllMissions();

        System.out.println("All mission(s):");
        for (Optional<Mission> optionalMission : allMissions) {
            if (optionalMission.isPresent()) {
                Mission mission = optionalMission.get();

                System.out.println("Mission:");
                System.out.println("code: " + mission.getCode());
                System.out.println("name: " + mission.getName());
                System.out.println("description: " + mission.getDescription());
                System.out.println();
            } else {
                System.out.println("No accounts found.");
            }
        }
    }
    public void addaffectation(){
        System.out.print("Enter matricule: ");
        String matriculeInput = scanner.nextLine();

        System.out.print("Enter mission code: ");
        int missionCode = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter end date of this affectation (dd-MM-yyyy): ");
        String endDateInput = scanner.nextLine();
        LocalDate endDate = null;

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            endDate = LocalDate.parse(endDateInput, dateFormatter);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format. Please use dd-MM-yyyy.");
        }


        affectation.setEmployee(new Employee());
        affectation.setMission(new Mission());

        affectation.getEmployee().setMatricule(matriculeInput);
        affectation.getMission().setCode(missionCode);
        affectation.setEndDate(endDate);

        Optional<Affectation> addedAffectation = affectationI.add(affectation);

        if (addedAffectation.isPresent()) {
            Affectation added = addedAffectation.get();
            System.out.println("Affectation added successfully!");
            System.out.println("Matricule: " + added.getEmployee().getMatricule());
            System.out.println("Mission Code: " + added.getMission().getCode());
            System.out.println("Start Date: " + added.getStartDate());
            System.out.println("End Date: " + added.getEndDate());
        } else {
            System.out.println("Failed to add the affectation.");
        }
    }
    public void historyaffectation(){
        List<Optional<Affectation>> allAffectations = affectationI.getallAffectations();

        System.out.println("History affectation(s):");
        for (Optional<Affectation> optionalAffectation : allAffectations) {
            if (optionalAffectation.isPresent()) {
                Affectation affectation = optionalAffectation.get();// extraire valeur

                System.out.println("Affectation:");
                System.out.println("Matricule: " + affectation.getEmployee().getMatricule());
                System.out.println("Mission Code: " + affectation.getMission().getCode());
                System.out.println("Start Date: " + affectation.getStartDate());
                System.out.println("End Date: " + affectation.getEndDate());
                System.out.println();
            } else {
                System.out.println("No affectations found.");
            }
        }
    }
    public void deleteaffectation(){
        List<Optional<Affectation>> allAffectations1 = affectationI.getallAffectations();
        System.out.print("Enter affectation matricule to delete: ");
        String affectationMatriculeToDelete = scanner.nextLine();

        System.out.print("Enter affectation code to delete: ");
        int affectationCodeToDelete = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter a start date (yyyy-MM-dd): ");
        String affectationStartDateToDelete = scanner.nextLine();

        LocalDate desiredDate2 = null;
        try {
            desiredDate2 = LocalDate.parse(affectationStartDateToDelete);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");

        }

        System.out.print("Enter an end date (yyyy-MM-dd): ");
        String affectationEndDateToDelete = scanner.nextLine();

        LocalDate desiredDate3 = null;
        try {
            desiredDate3 = LocalDate.parse(affectationEndDateToDelete);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");

        }

        LocalDate finalDesiredDate = desiredDate2;
        LocalDate finalDesiredDate1 = desiredDate3;
        Optional<Affectation> affectationOptional = allAffectations1.stream()
                .filter(affectation -> affectation.isPresent() &&
                        affectation.get().getEmployee().getMatricule().equals(affectationMatriculeToDelete) &&
                        affectation.get().getMission().getCode().equals(affectationCodeToDelete) &&
                        affectation.get().getStartDate().equals(finalDesiredDate) &&
                        affectation.get().getEndDate().equals(finalDesiredDate1))
                .map(Optional::get)// extract if present
                .findFirst();

        Optional<Boolean> deleteAffectationResult = affectationI.deleteAffectationByCode(affectationCodeToDelete, affectationMatriculeToDelete, desiredDate2, desiredDate3);

        if (deleteAffectationResult.isPresent()) {
            if (deleteAffectationResult.get()) {
                System.out.println("Affectation deleted successfully.");
            } else {
                System.out.println("Affectation not found with the specified code.");
            }
        } else {
            System.out.println("Failed to delete the affectation.");
        }
    }
    public void statistics(){
        Map<String, Integer> roleCounts = new HashMap<>();

        int directorsCount = affectationI.getDirectorsCount().orElse(0);
        int accountManagersCount = affectationI.getAccountManagersCount().orElse(0);

        roleCounts.put("Directors", directorsCount);
        roleCounts.put("Account Managers", accountManagersCount);

        String report = "Bank Report:\n";
        for (Map.Entry<String, Integer> entry : roleCounts.entrySet()) {
            report += entry.getKey() + ": " + entry.getValue() + "\n";
        }

        System.out.println("______________________________");
        System.out.println(report);
        System.out.println("______________________________");
    }



}
