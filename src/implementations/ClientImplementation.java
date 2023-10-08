package implementations;
import dto.Employee;
import interfaces.ClientInterface;
import helper.DatabaseConnection;
import dto.Client;
import java.time.LocalDate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
public class ClientImplementation implements ClientInterface {
    private DatabaseConnection db;

    public ClientImplementation() {
        db = DatabaseConnection.getInstance();
    }


      /*  public static int generateUniqueCode() {
            Random random = new Random();
            int min = 1000; // Minimum 4-digit code
            int max = 9999; // Maximum 4-digit code
            return random.nextInt(max - min + 1) + min;
        }*/

        @Override
    //    public Client add(Client client) {
            // Generate a random matricule
          /*  Integer generatedCode = generateUniqueCode();
            client.setCode(generatedCode);*/


  /*          try {
                Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO client ( name, prenoun, birthDate, phone, adresse) " +
                                "VALUES ( ?, ?, ?, ?, ?)"
                );

               // preparedStatement.setInt(1, client.getCode());
                preparedStatement.setString(1, client.getName());
                preparedStatement.setString(2, client.getPrenoun());
                preparedStatement.setDate(3, java.sql.Date.valueOf(client.getBirthDate()));
                preparedStatement.setString(4, client.getPhone());
                preparedStatement.setString(5, client.getAdresse());

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 1) {
                    return client; // Return the inserted employee object
                } else {
                    return null; // Insert failed
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null; // Exception occurred
            }
        }*/


    public Optional<Client> add(Client client) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO client ( name, prenoun, birthDate, phone, adresse) " +
                            "VALUES ( ?, ?, ?, ?, ?)"
            );

            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getPrenoun());
            preparedStatement.setDate(3, java.sql.Date.valueOf(client.getBirthDate()));
            preparedStatement.setString(4, client.getPhone());
            preparedStatement.setString(5, client.getAdresse());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                return Optional.of(client); // Return an Optional containing the inserted client
            } else {
                return Optional.empty(); // Insert failed, return an empty Optional
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty(); // Exception occurred, return an empty Optional
        }
    }

    public Client searchByCode(Integer searchCode){
        try {
            Connection conn = db.getConnection();
            String searchQuery = "SELECT * FROM client WHERE code = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(searchQuery)) {
                preparedStatement.setInt(1, searchCode);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Integer foundCode = resultSet.getInt("code");
                    String name = resultSet.getString("name");
                    String prenoun = resultSet.getString("prenoun");
                    String adresse = resultSet.getString("adresse");
                    String phone = resultSet.getString("phone");
                    LocalDate birthdate = resultSet.getDate("birthdate").toLocalDate();
                    return new Client(name, prenoun, birthdate, phone, foundCode, adresse);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean deleteByCode(Integer deleteCode){
        Connection conn = db.getConnection();
        String deleteQuery = "DELETE FROM client WHERE code = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);

            preparedStatement.setInt(1, deleteCode);

            int rowsDeleted = preparedStatement.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Client> getAllClients(){
        Connection conn = db.getConnection();
        String selectQuery = "SELECT * FROM client";
        List<Client> clients = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
            ResultSet resultSet = preparedStatement.executeQuery();



            while (resultSet.next()) {
                Client client = new Client();
                client.setCode(resultSet.getInt("code"));
                client.setName(resultSet.getString("name"));
                client.setPrenoun(resultSet.getString("prenoun"));

                client.setPhone(resultSet.getString("phone"));
                client.setBirthDate(resultSet.getDate("birthdate").toLocalDate());
                client.setAdresse(resultSet.getString("adresse"));
                clients.add(client);
            }

            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Client update(Client clientToUpdate){
        String updateQuery = "UPDATE client SET name = ?, prenoun = ?, adresse = ?, phone = ?, birthdate = ? WHERE code = ?";
        try {
            Connection conn = db.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);

            preparedStatement.setString(1, clientToUpdate.getName());
            preparedStatement.setString(2, clientToUpdate.getPrenoun());
            preparedStatement.setString(3, clientToUpdate.getAdresse());
            preparedStatement.setString(4, clientToUpdate.getPhone());

            preparedStatement.setDate(5, java.sql.Date.valueOf(clientToUpdate.getBirthDate()));
            preparedStatement.setInt(6, clientToUpdate.getCode());
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                return clientToUpdate;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Client> searchClient(String searchValue2){
        Connection conn = db.getConnection();

        String searchQuery = "SELECT * FROM client WHERE " +
                "name ILIKE ? OR " +
                "prenoun ILIKE ? OR " +
                "adresse ILIKE ? OR " +
                "phone ILIKE ? OR " +
                "birthdate::text ILIKE ?";


        List<Client> foundClients = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(searchQuery);

            for (int i = 1; i <= 4; i++) {
                preparedStatement.setString(i, "%" + searchValue2 + "%");
            }

            preparedStatement.setString(5, "%" + searchValue2 + "%");
 //           preparedStatement.setString(6, "%" + searchValue2 + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Client client = new Client();
                client.setCode(resultSet.getInt("code"));
                client.setName(resultSet.getString("name"));
                client.setPrenoun(resultSet.getString("prenoun"));
                client.setAdresse(resultSet.getString("adresse"));
                client.setPhone(resultSet.getString("phone"));
                client.setBirthDate(resultSet.getDate("birthdate").toLocalDate());
                foundClients.add(client);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foundClients;

    }

}
