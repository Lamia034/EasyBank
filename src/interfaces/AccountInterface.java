package interfaces;
import dto.CurrentAccount;
import dto.SavingAccount;

public interface AccountInterface {
    CurrentAccount addcurrent(CurrentAccount currentaccount);
    SavingAccount addsaving(SavingAccount savingaccount);
    boolean deleteByNumber(Integer deleteNumber);
}
