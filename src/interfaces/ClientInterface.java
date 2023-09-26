package interfaces;
import dto.Client;
public interface ClientInterface {
    Client add(Client client);
    Client searchByCode(Integer searchCode);
    boolean deleteByCode(Integer deleteCode);
}
