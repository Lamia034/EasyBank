package interfaces;
import dto.CurrentAccount;
import dto.SavingAccount;

public interface AccountInterface {
    CurrentAccount addcurrent(String employeeMatricule,Integer clientCode,CurrentAccount currentaccount);
    SavingAccount addsaving(String employeeMatricule,Integer clientCode,SavingAccount savingaccount);
    boolean deleteByNumber(Integer deleteNumber);
}
