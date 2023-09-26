package implementations;
import interfaces.ClientInterface;
import helper.DatabaseConnection;
import dto.Client;
import java.time.LocalDate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
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
        public Client add(Client client) {
            // Generate a random matricule
          /*  Integer generatedCode = generateUniqueCode();
            client.setCode(generatedCode);*/


            try {
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
        }
//hi

}
