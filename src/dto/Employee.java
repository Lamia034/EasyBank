package dto;
import java.time.LocalDate;
import java.util.List;

public class Employee extends Person{
    private String matricule;

    private LocalDate hiringDate;
    private String email;

    private List<Affectation> affecting;

    public Employee(){
        super();
    }

    public Employee(String name, String prenoun, LocalDate birthDate, String phone, String matricule, LocalDate hiringDate, String email) {
        super(name, prenoun , birthDate, phone);
        this.matricule = matricule;
        this.hiringDate = hiringDate;
        this.email = email;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    // Getter and setter for hiringDate
    public LocalDate getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(LocalDate hiringDate) {
        this.hiringDate = hiringDate;
    }

    // Getter and setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
