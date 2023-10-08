package interfaces;
import dto.Account;
import dto.CurrentAccount;
import dto.SavingAccount;

import java.util.List;
import java.util.Optional;

public interface AccountInterface {
    CurrentAccount addcurrent(String employeeMatricule,Integer clientCode,CurrentAccount currentaccount);
    SavingAccount addsaving(String employeeMatricule,Integer clientCode,SavingAccount savingaccount);
    boolean deleteByNumber(Integer deleteNumber);
    List<Account> searchByCode(Integer searchCode2);
    List<Optional<Account>> getAllAccounts();
    Optional<Account> updateAccount(Account account);
}
