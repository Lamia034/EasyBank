package implementations;

import dto.Agency;
import helper.DatabaseConnection;
import interfaces.GenericInterface;

import java.sql.*;
import java.util.Optional;

public class AgencyImplementation implements GenericInterface<Agency> {
    @Override
    public Optional<Agency> add(Agency agency) {
        String insertQuery = "INSERT INTO agency (name, adresse , phone) VALUES (?, ? , ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, agency.getName());
            preparedStatement.setString(2, agency.getAdresse());
            preparedStatement.setString(3, agency.getPhone());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedAgencyCode = generatedKeys.getInt(1);
                    agency.setCode(generatedAgencyCode);
                    return Optional.of(agency);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();

    }
}
