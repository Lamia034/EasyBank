package dto;
import java.time.LocalDate;
public class Affectation {
    private LocalDate startDate;
    private LocalDate endDate;

    private Employee employee;
    private Mission mission;

    public Affectation(){}
    public Affectation(LocalDate startDate, LocalDate endDate, Employee employee , Mission mission){
        this.startDate = startDate;
        this.endDate = endDate;
        this.employee = employee;
        this.mission = mission;
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
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }


}