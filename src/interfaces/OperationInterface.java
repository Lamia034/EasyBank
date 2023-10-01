package interfaces;

import dto.Operation;

import java.util.Optional;

public interface OperationInterface {
    Optional<Operation> add(Operation operation);
    Optional<Boolean> deleteOperationByNumber(int operationNumber);
}
