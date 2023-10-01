package implementations;

import dto.Affectation;
import helper.DatabaseConnection;
import interfaces.AffectationInterface;

import java.sql.*;
import java.util.Optional;
import helper.DatabaseConnection;

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

    }

