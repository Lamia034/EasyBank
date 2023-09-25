package dto;
import java.time.LocalDate;
public class Affectation {
    private LocalDate startDate;
    private LocalDate endDate;

    private Employee employee;
    private Mission mission;

    public Affectation(){}
    public Affectation(LocalDate startDate, LocalDate endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate(){
        return startDate;
    }
    public void setStartDate(LocalDate startDate){
        this.startDate = startDate;
    }

    public LocalDate getEndDate(){
        return endDate;
    }
    public void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    }


}