package dto;

import java.time.LocalDate;

public abstract class Account {
    private Integer number;
    private float balance;
    private LocalDate creationDate;
    private AccountStatus status;

    public Account(){

    }

    public Account(Integer number, float balance, LocalDate creationDate, AccountStatus status) {
        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
        this.status = status;
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



    public enum AccountStatus {
        ACTIVE, INACTIVE; // Corrected enum values to follow naming conventions
    }
}

