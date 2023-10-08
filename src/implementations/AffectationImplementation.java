package implementations;

import dto.Affectation;
import dto.Employee;
import dto.Mission;
import helper.DatabaseConnection;
import interfaces.AffectationInterface;

import java.sql.*;
import java.util.Optional;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class AffectationImplementation implements AffectationInterface {
    private DatabaseConnection db;

    public AffectationImplementation() {
        db = DatabaseConnection.getInstance();
    }

        @Override
        public Optional<Affectation> add(Affectation affectation) {
            try (Connection conn = db.getConnection()) {
                LocalDate currentDate = LocalDate.now();
                affectation.setStartDate(currentDate);
                // Insert the affectation into the database
                String insertQuery = "INSERT INTO affectation (employeematricule, missioncode, startdate, enddate) VALUES (?, ?, ?, ?)";
                try (PreparedStatement insertStatement = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                    insertStatement.setString(1, affectation.getEmployee().getMatricule());
                    insertStatement.setInt(2, affectation.getMission().getCode());
                    insertStatement.setDate(3, java.sql.Date.valueOf(affectation.getStartDate()));
                    insertStatement.setDate(4, java.sql.Date.valueOf(affectation.getEndDate()));

                    int rowsInserted = insertStatement.executeUpdate();

                    if (rowsInserted > 0) {

                            return Optional.of(affectation);

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return Optional.empty();
        }

    @Override
    public List<Optional<Affectation>> getallAffectations() {
        List<Optional<Affectation>> affectations = new ArrayList<>();

        try (Connection conn = db.getConnection()) {
            String query = "SELECT * FROM affectation";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int code = resultSet.getInt("missioncode");
                    String matricule = resultSet.getString("employeematricule");
                    LocalDate startdate = resultSet.getDate("startdate").toLocalDate();
                    LocalDate enddate = resultSet.getDate("enddate").toLocalDate();

                    Affectation affectation = new Affectation();
                    affectation.setStartDate(startdate);
                    affectation.setEndDate(enddate);

                    // Check if mission code is not null
                    if (!resultSet.wasNull()) {
                        Mission mission = new Mission();
                        mission.setCode(code);
                        affectation.setMission(mission);
                    }

                    // Check if matricule is not null
                    if (matricule != null) {
                        Employee employee = new Employee();
                        employee.setMatricule(matricule);
                        affectation.setEmployee(employee);
                    }

                    Optional<Affectation> optionalAffectation = Optional.of(affectation);

                    affectations.add(optionalAffectation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return affectations;
    }
    @Override
    public Optional<Boolean> deleteAffectationByCode(Integer affectationCodeToDelete, String affectationMatriculeToDelete, LocalDate affectationStartDateToDelete, LocalDate affectationEndDateToDelete) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String deleteAffectationSQL = "DELETE FROM affectation WHERE missioncode = ? AND employeematricule = ? AND startdate = ? AND enddate = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteAffectationSQL)) {
            pstmt.setInt(1, affectationCodeToDelete);
            pstmt.setString(2, affectationMatriculeToDelete);
            pstmt.setObject(3, affectationStartDateToDelete);
            pstmt.setObject(4, affectationEndDateToDelete);
            int affectedRows = pstmt.executeUpdate();
            return Optional.of(affectedRows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> getAccountManagersCount() {
        int count = 0;

        try (Connection conn = db.getConnection()) {
            String query = "SELECT COUNT(*) FROM affectation WHERE missioncode = 1";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.of(count);
    }
@Override
public Optional<Integer> getDirectorsCount(){
    int count = 0;

    try (Connection conn = db.getConnection()) {
        String query = "SELECT COUNT(*) FROM affectation WHERE missioncode = 2";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return Optional.of(count);
}

}

