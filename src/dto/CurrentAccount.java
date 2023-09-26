package dto;

import java.time.LocalDate;

public class CurrentAccount extends Account{

    private float overdraft;

    public CurrentAccount(){
        super();
    }
    public CurrentAccount(Integer number, float balance, LocalDate creationDate, AccountStatus status ,Employee employee , Client client , float overdraft){
        super( number, balance, creationDate, status , employee, client);
        this.overdraft = overdraft;
    }

    public float getOverdraft(){
        return overdraft;
    }
    public void setOverdraft(float overdraft){
        this.overdraft = overdraft;
    }
}