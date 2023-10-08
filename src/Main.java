import dto.*;
import helper.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import implementations.*;
import services.AgencyService;
import services.Services;
import views.AgencyView;

import java.time.format.DateTimeFormatter;


import java.util.stream.Collectors;


public class Main {
    static Services services = new Services();
   // static AgencyView agencyview = new AgencyView();


    public static void main(String[] args) throws SQLException {
        // Create a DatabaseConnection instance
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        // Create an AgencyImplementation instance
        AgencyImplementation agencyImplementation = new AgencyImplementation();

        // Create an AgencyService instance, injecting the AgencyImplementation dependency
        AgencyService agencyService = new AgencyService(agencyImplementation);

        // Create an AgencyView instance, injecting the AgencyService dependency
        AgencyView agencyview = new AgencyView(agencyService);



        Connection conn = dbConnection.getConnection();
        System.out.println(conn);


        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean exit = false;
        do {

            System.out.println("************************************");
            System.out.println("*   EasyBank Management System      *");
            System.out.println("************************************");
            System.out.println("* Options:                          *");
            System.out.println("* 1. Add Employee                   *");
            System.out.println("* 2. Find Employee by Matricul      *");
            System.out.println("* 3. Delete Employee                *");
            System.out.println("* 4. List All Employees             *");
            System.out.println("* 5. Update Employee                *");
            System.out.println("* 6. Find Employee by Information   *");
            System.out.println("* 7. Add Client                     *");
            System.out.println("* 8. Find Client by Code            *");
            System.out.println("* 9. Delete Client                  *");
            System.out.println("* 10. Display All Clients           *");
            System.out.println("* 11. Update Client                 *");
            System.out.println("* 12. Find Client by Information    *");
            System.out.println("* 13. Add Current Account           *");
            System.out.println("* 14. Add Saving Account            *");
            System.out.println("* 15. Delete Account                *");
            System.out.println("* 16. Find Account by Client        *");
            System.out.println("* 17. Display All Accounts          *");
            System.out.println("* 18. Display Accounts by Status    *");
            System.out.println("* 19. Display Accounts by Creation  *");
            System.out.println("* 20. Change Account Status         *");
            System.out.println("* 21. Update Account                *");
            System.out.println("* 22. Add Operation                 *");
            System.out.println("* 23. Delete Operation              *");
            System.out.println("* 24. Find Operation                *");
            System.out.println("* 25. Add Mission                   *");
            System.out.println("* 26. Delete Mission                *");
            System.out.println("* 27. Display List of Missions      *");
            System.out.println("* 28. Create Affectation            *");
            System.out.println("* 29. Display History Affectations  *");
            System.out.println("* 30. Delete Affectation            *");
            System.out.println("* 31. Statistics Affectations       *");
            System.out.println("* 32. add new agency                *");
            System.out.println("* 40. Exit                          *");
            System.out.println("************************************");


            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: // Add Employee
                services.addemployee();
                    break;

                case 2://Find Employee
                    services.findemployeebymatricul();
                    break;
                case 3://dalete employee
                    services.deleteemployee();
                    break;
                case 4: // List ALL employees
                    services.listallemployees();
                    break;
                case 5://update employee
                services.updateemployee();
                    break;
                case 6: // Find Employee by attributes
                    services.findemployeebyattributs();
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
                services.addclient();
                    break;

                case 8://Find Client
                services.findclient();
                    break;
                case 9://dalete client
                 services.deleteclient();
                    break;
                case 10: // List ALL clients
                services.listallclients();
                    break;
                case 11://update client
                services.updateclient();
                    break;
                case 12: // Find client by attributes
                services.findclientbyattributs();
                    break;
                case 13://add current account
                services.addcurrentaccount();
                    break;

                case 14://add saving account
                services.addsavingaccount();
                    break;
                case 15://dalete account
                     services.deleteaccount();
                    break;
                case 16: // Find account by Client
                services.findaccountbyclient();
                    break;

                case 17: // List ALL accounts
                services.listallaccounts();
                    break;
                case 18://filter accounts by choosen status
                services.filteraccountsbychoosenstatus();
                    break;


                case 19:// filter accounts by creation date
               services.filteraccountsbycreationdate();
                    break;
                case 20://change account status
                services.changeaccountstatus();
                    break;

                case 21://update account by his number
                services.updateaccountbyitsnumber();
                    break;
                case 22: // Add operation
                 services.addoperation();
                    break;


                case 23: // Delete operation
                services.deleteoperation();
                    break;
                case 24: // Search operation by number
                services.searchoperationbynumber();
                    break;
                case 25: // Add mission
                services.addmission();
                    break;
                case 26: // Delete mission
                services.deletemission();
                    break;
                case 27: // List ALL missions
                services.listallmissions();


                    break;
                case 28: // Add affectation
                 services.addaffectation();
                    break;
                case 29: // Display history affectations
                services.historyaffectation();

                    break;
                case 30: // Delete affectation
                services.deleteaffectation();
                    break;

          /*      case 31:
                    int directorsCount = affectationI.getDirectorsCount().orElse(0);
                    int accountManagersCount = affectationI.getAccountManagersCount().orElse(0);

                    String report = "Bank Report:\n" +
                            "Directors: " + directorsCount + "\n" +
                            "Account Managers: " + accountManagersCount;

                    System.out.println("______________________________");
                    System.out.println(report);
                    System.out.println("______________________________");
                    break;*/
                case 31:// affectation statistics
                 services.statistics();
                    break;
                case 32:
                    agencyview.addagency();
                    break;
                case 40:
                    exit = true;
                    break;


            }

        }   while (!exit) ;


        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
