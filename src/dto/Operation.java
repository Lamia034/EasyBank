package dto;
import java.time.LocalDate;

public class Operation {

    private Integer number;
    private LocalDate creationDate;

    private float montant;
    private typeOperation type;
    private Employee employee;
    private Account account;

    public Operation(){}
    public Operation(Integer number, LocalDate creationDate, float montant, typeOperation type, Employee employee, Account account){
        this.number = number;
        this.creationDate = creationDate;
        this.montant = montant;
        this.type = type;
        this.employee = employee;
        this.account = account;
    }

    public Integer getNumber(){
        return number;
    }
    public void setNumber(Integer number){
        this.number = number;
    }

    public LocalDate getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(LocalDate creationDate){
        this.creationDate = creationDate;
    }

    public float getMontant(){
        return montant;
    }
    public void setMontant(float montant){
        this.montant = montant;
    }

    public typeOperation getType(){
        return type;
    }
    public void setType(typeOperation type){
        this.type = type;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // Getter and setter for client
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public enum typeOperation{
       PAYEMENT , WITHDRAW ;
    }
}
