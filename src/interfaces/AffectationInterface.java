package interfaces;

import dto.Affectation;

import java.util.Optional;

public interface AffectationInterface {
    Optional<Affectation> add(Affectation affectation);
}
