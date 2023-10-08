package interfaces;

import dto.Affectation;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AffectationInterface {
    Optional<Affectation> add(Affectation affectation);
    List<Optional<Affectation>> getallAffectations();
    Optional<Boolean> deleteAffectationByCode(Integer affectationCodeToDelete  , String affectationMatriculeToDelete, LocalDate affectationStartDateToDelete, LocalDate affectationEndDateToDelete);
    Optional<Integer> getAccountManagersCount();
    Optional<Integer> getDirectorsCount();
}
