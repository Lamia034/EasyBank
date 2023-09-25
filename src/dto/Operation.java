package dto;
import java.time.LocalDate;

public class Operation {

    private Integer number;
    private LocalDate creationDate;

    private float montant;
    private typeOperation type;

    public Operation(){}
    public Operation(Integer number, LocalDate creationDate, float montant, typeOperation type){
        this.number = number;
        this.creationDate = creationDate;
        this.montant = montant;
        this.type = type;
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

    public enum typeOperation{
       PAYEMENT , WITHDRAW ;
    }
}
