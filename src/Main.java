import helper.DatabaseConnection;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Scanner;
import dto.Account;
import dto.Affectation;
import dto.Client;
import dto.CurrentAccount;
import dto.SavingAccount;
import dto.Employee;
import dto.Mission;
import dto.Operation;
import dto.Person;
import implementations.EmployeeImplementation;
import implementations.ClientImplementation;
import java.time.format.DateTimeFormatter;


import java.util.List;


public class Main {
    static Employee employee = new Employee();
    static EmployeeImplementation employeeI = new EmployeeImplementation();
    static Client client = new Client();
    static ClientImplementation clientI = new ClientImplementation();

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
                    } catch (java.time.format.DateTimeParseException e) {
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