package interfaces;

import java.util.Optional;

public interface GenericInterface<T> {
    Optional<T> add(T t);
}
