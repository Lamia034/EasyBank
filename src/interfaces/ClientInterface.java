package interfaces;
import dto.Client;
import java.util.List;
import java.util.Optional;

public interface ClientInterface {
   // Client add(Client client);
   Optional<Client> add(Client client);
    Client searchByCode(Integer searchCode);
    boolean deleteByCode(Integer deleteCode);
    List<Client> getAllClients();
    Client update(Client clientToUpdate);
    List<Client> searchClient(String searchValue2);
}
