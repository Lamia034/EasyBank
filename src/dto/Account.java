package dto;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
public abstract class Account {
    private Integer number;
    private float balance;
    private LocalDate creationDate;
    private AccountStatus status;
    private Employee employee;
    private Client client;
    private List<Operation> operations;
    public Account(){
        operations = new ArrayList<>();
    }

    public Account(Integer number, float balance, LocalDate creationDate, AccountStatus status , Employee employee , Client client) {
        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
        this.status = status;
        this.employee = employee;
        this.client = client;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    // Getter and setter for employee
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // Getter and setter for client
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public enum AccountStatus {
        ACTIF, INACTIF;
    }
}

