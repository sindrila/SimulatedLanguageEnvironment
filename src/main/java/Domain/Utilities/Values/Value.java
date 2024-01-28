package Domain.Utilities.Values;

import Domain.Utilities.Types.Type;

public interface Value {
    Type getType();
    String toString();
    Value deepCopy();
}
