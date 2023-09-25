package interfaces;
import dto.Employee;
import java.util.List;
public interface EmployeeInterface {
    Employee add(Employee employee);
    Employee searchByMatricule(String searchMatricule);
    boolean deleteByMatricule(String deleteMatricule);
    List<Employee> getAllEmployees();
    Employee update(Employee employeeToUpdate);
    List<Employee> searchEmployee(String searchValue);
}
