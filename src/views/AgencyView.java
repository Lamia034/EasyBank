package views;

import dto.Agency;
import services.AgencyService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

public class AgencyView {
    Agency agency = new Agency();
  //  AgencyService agencyservice = new AgencyService();
    private final AgencyService agencyservice;
    public AgencyView(AgencyService agencyservice){
        this.agencyservice = agencyservice;
    }
    Scanner scanner = new Scanner(System.in);

    public void addagency() {
        System.out.print("Enter agency name: ");
        agency.setName(scanner.nextLine());

        System.out.print("Enter agency adresse: ");
        agency.setAdresse(scanner.nextLine());

        System.out.print("Enter agency phone: ");
        agency.setPhone(scanner.nextLine());

            Optional<Agency> addedAgency = agencyservice.addagency(agency);

            if (addedAgency.isPresent()) {
                System.out.println("agency added successfully with code: " +agency.getCode()+  "name: " + agency.getName() + " adresse: " + agency.getAdresse() + " phone: " + agency.getPhone());
            } else {
                System.out.println("failed to add new agency");
            }

    }
}
