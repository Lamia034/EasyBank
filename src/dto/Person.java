package dto;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Person {
    protected String name;
    protected String prenoun;
    protected LocalDate birthDate;

    protected String phone;

    public Person(){

    }

    public Person(String name, String prenoun, LocalDate birthDate, String phone) {
        this.name = name;
        this.prenoun = prenoun;
        this.birthDate = birthDate;
        this.phone = phone;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrenoun() {
        return prenoun;
    }

    public void setPrenoun(String prenoun) {
        this.prenoun = prenoun;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}