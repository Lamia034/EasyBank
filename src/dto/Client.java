package dto;

import java.time.LocalDate;

public class Client extends Person{
    private Integer code;

    private String adresse;

    public Client(){
        super();
    }

    public Client(String name, String prenoun, LocalDate birthDate, String phone, Integer code, String adresse) {
        super(name, prenoun , birthDate, phone);
        this.code = code;
        this.adresse = adresse;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    // Getter and setter for hiringDate
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

}
