package dto;

import java.time.LocalDate;

public class CurrentAccount extends Account{

    private float decouvert;

    public CurrentAccount(){
        super();
    }
    public CurrentAccount(Integer number, float balance, LocalDate creationDate, AccountStatus status , float decouvert){
        super( number, balance, creationDate, status);
        this.decouvert = decouvert;
    }

    public float getDecouvert(){
        return decouvert;
    }
    public void setDecouvert(float decouvert){
        this.decouvert = decouvert;
    }
}