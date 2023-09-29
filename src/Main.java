import dto.*;
import helper.DatabaseConnection;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

import implementations.EmployeeImplementation;
import implementations.ClientImplementation;
import implementations.AccountImplementation;

import java.time.format.DateTimeFormatter;


import java.util.List;
import java.util.stream.Collectors;


public class Main {
    static Employee employee = new Employee();
    static EmployeeImplementation employeeI = new EmployeeImplementation();
    static Client client = new Client();
    static ClientImplementation clientI = new ClientImplementation();

    static CurrentAccount currentaccount = new CurrentAccount();
    static SavingAccount savingaccount = new SavingAccount();
    static AccountImplementation accountI = new AccountImplementation();

    public static void main(String[] args) {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();


        Connection conn = dbConnection.getConnection();
        System.out.println(conn);


        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean exit = false;
        do {

            System.out.println("-EasyBank Management System-");
            System.out.println("1. Add Employee");//done
            System.out.println("2. Find Employee by matricul");//done
            System.out.println("3. Dalete Employee");//done
            System.out.println("4. List all Employees");//done
            System.out.println("5. Update Employee");//done
            System.out.println("6. Find Employee By any of it's informations!");//done
            System.out.println("7. Add Client");//done
            System.out.println("8. Find Client By Code");//done
            System.out.println("9. Delete Client");//done
            System.out.println("10. Display all Clients");//done
            System.out.println("11. Update Client");//done
            System.out.println("12. Find Client by any of it's informations");//done
            System.out.println("13. Add current account");//done
            System.out.println("14. Add saving account");//done
            System.out.println("15. Delete account");//done
            System.out.println("16. find account by client");//done
            System.out.println("17. display all accounts");//done
            System.out.println("18. display accounts by status");//done
            System.out.println("19. display accounts by creation date");//done

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: // Add Employee
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
                        break;
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
                    break;

                case 2://Find Employee
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
                    break;
                case 3://dalete employee
                    System.out.print("Enter employee matricule to delete: ");
                    String deleteMatricule = scanner.nextLine();

                    boolean deleted = employeeI.deleteByMatricule(deleteMatricule);

                    if (deleted) {
                        System.out.println("Employee with Matricule " + deleteMatricule + " deleted successfully.");
                    } else {
                        System.out.println("Employee with Matricule " + deleteMatricule + " not found or deletion failed.");
                    }
                    break;
                case 4: // List ALL Books
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
                    break;
                case 5://update employee
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
                                System.out.println("employee updated successfully! with name:" + employeeToUpdate.getName() + " , prenoun " + employeeToUpdate.getPrenoun() + " , email " + employeeToUpdate.getEmail() + " , phone " + employeeToUpdate.getPhone()  + " , birth date " + employeeToUpdate.getBirthDate()+ " , hiring date " + employeeToUpdate.getHiringDate()   );
                            } else {
                                System.out.println("Failed to update the employee.");
                            }
                        } else {
                            System.out.println("employee not found with Number: " + updateMatricule);
                        }


                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid matricule.");
                    }



                    break;
                case 6: // Find Employee by attributes
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
                    break;


           /*     case 7: // Add Client
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
                    } catch (java.time.format.DateTimeParseException e) {
                        System.err.println("Invalid date format. Please use dd-MM-yyyy.");
                        break;
                    }

                    client.setBirthDate(clientBirthDate);


                    System.out.print("Enter client Phone: ");
                    client.setPhone(scanner.nextLine());

                    System.out.print("Enter adresse : ");
                    client.setAdresse(scanner.nextLine());

                    if (clientI.add(client) != null) {
                        System.out.println("Client added successfully!:");
                        System.out.println("name: " + client.getName() + ",prenoun: " + client.getPrenoun() + ",Birth date: " + client.getBirthDate() + " phone: " + client.getPhone()  + " code: " + client.getCode() + " adresse: " + client.getAdresse());
                    } else {
                        System.out.println("Failed to add the employee.");
                    }
                    break;*/
                case 7: // Add Client
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
                        break;
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
                        System.out.println("name: " + addedClient.getName() + ",prenoun: " + addedClient.getPrenoun() + ",Birth date: " + addedClient.getBirthDate() + " phone: " + addedClient.getPhone()  + " code: " + addedClient.getCode() + " adresse: " + addedClient.getAdresse());
                    } else {
                        System.out.println("Failed to add the client.");
                    }
                    break;

                case 8://Find Client
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
                    break;
                case 9://dalete client
                    System.out.print("Enter client code to delete: ");
                    Integer deleteCode = scanner.nextInt();
                    scanner.nextLine();

                    boolean deletedc = clientI.deleteByCode(deleteCode);

                    if (deletedc) {
                        System.out.println("Client with Code " + deleteCode + " deleted successfully.");
                    } else {
                        System.out.println("Client with Code " + deleteCode + " not found or deletion failed.");
                    }
                    break;
                case 10: // List ALL clients
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
                    break;
                case 11://update client
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
                                System.out.println("client updated successfully! with name:" + clientToUpdate.getName() + " , prenoun " + clientToUpdate.getPrenoun() + " , adresse " + clientToUpdate.getAdresse() + " , phone " + clientToUpdate.getPhone()  + " , birth date " + clientToUpdate.getBirthDate() );
                            } else {
                                System.out.println("Failed to update the client.");
                            }
                        } else {
                            System.out.println("client not found with Number: " + updateCode);
                        }


                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid code.");
                    }
                    break;
                case 12: // Find client by attributes
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
                    break;
                case 13://add current account
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

                    if (accountI.addcurrent(employeeMatricule,clientCode, currentaccount) != null) {
                        System.out.println("Current account added successfully!:");
                        System.out.println("account number: " + currentaccount.getNumber() + ",balance: " + currentaccount.getBalance() + ",Creation date: " + currentaccount.getCreationDate() + " status: " + currentaccount.getStatus() + ", Overdraft: " + currentaccount.getOverdraft()  );
                    } else {
                        System.out.println("Failed to add the account.");
                    }
                    break;

                case 14://add saving account
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

                    if (accountI.addsaving(employeeMatriculee,clientCodee,savingaccount) != null) {
                        System.out.println("saving account added successfully!:");
                        System.out.println("account number: " + savingaccount.getNumber() + ",balance: " + savingaccount.getBalance() + ",Creation date: " + savingaccount.getCreationDate() + " status: " + savingaccount.getStatus() + ", Overdraft: " + savingaccount.getInterestRate()  );
                    } else {
                        System.out.println("Failed to add the account.");
                    }
                    break;
                case 15://dalete account
                    System.out.print("Enter account's number to delete: ");
                    Integer deleteNumber = scanner.nextInt();
                    scanner.nextLine();

                    boolean deletedacc = accountI.deleteByNumber(deleteNumber);

                    if (deletedacc) {
                        System.out.println("account with number " + deleteNumber + " deleted successfully.");
                    } else {
                        System.out.println("account with number " + deleteNumber + " not found or deletion failed.");
                    }
                    break;
                case 16: // Find account by Client
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
                    break;

                case 17: // List ALL accounts
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
                    break;
                case 18:


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
                            .filter(optionalAccount -> optionalAccount.isPresent()) // Filter out empty optionals
                            .map(Optional::get) // Get the Account from the Optional
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
                    }else{
                        System.out.println("no account found with this status");
                    }
                    break;





            }

        } while (!exit);


        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}