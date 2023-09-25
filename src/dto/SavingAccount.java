package dto;

import java.time.LocalDate;

public class SavingAccount extends Account{

    private float interestRate;

    public SavingAccount(){
        super();
    }
    public SavingAccount(Integer number, float balance, LocalDate creationDate, AccountStatus status , float interestRate){
        super( number, balance, creationDate, status);
        this.interestRate = interestRate;
    }

    public float getInterestRate(){
        return interestRate;
    }
    public void setInterestRate(float interestRate){
        this.interestRate = interestRate;
    }
}
