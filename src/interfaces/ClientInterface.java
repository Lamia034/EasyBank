package interfaces;
import dto.Client;
import java.util.List;
public interface ClientInterface {
    Client add(Client client);
    Client searchByCode(Integer searchCode);
    boolean deleteByCode(Integer deleteCode);
    List<Client> getAllClients();
    Client update(Client clientToUpdate);
}
