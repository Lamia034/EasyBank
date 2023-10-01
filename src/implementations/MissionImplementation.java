package implementations;

import dto.Mission;
import helper.DatabaseConnection;
import interfaces.MissionInterface;

import java.sql.*;
import java.util.Optional;

public class MissionImplementation implements MissionInterface {
    private DatabaseConnection db;

    public MissionImplementation() {
        db = DatabaseConnection.getInstance();
    }
@Override
public Optional<Mission> addMission(Mission mission) {
    String insertQuery = "INSERT INTO mission (name, description) VALUES (?, ?)";

    try (Connection conn = DatabaseConnection.getInstance().getConnection();
         PreparedStatement preparedStatement = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

        preparedStatement.setString(1, mission.getName());
        preparedStatement.setString(2, mission.getDescription());

        int rowsInserted = preparedStatement.executeUpdate();

        if (rowsInserted > 0) {
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedMissionCode = generatedKeys.getInt(1);
                mission.setCode(generatedMissionCode);
                return Optional.of(mission);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return Optional.empty();
}
    @Override
    public Optional<Boolean> deleteMissionByCode(Integer missionCodeToDelete) {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        String deleteMissionSQL = "DELETE FROM mission WHERE code = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteMissionSQL)) {
            pstmt.setInt(1, missionCodeToDelete);
            int affectedRows = pstmt.executeUpdate();
            return Optional.of(affectedRows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


}


