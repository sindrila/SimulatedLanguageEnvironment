package Domain.Utilities.Types;
import Domain.Utilities.Values.Value;

public interface Type {
    boolean equals(Object another);
    Value defaultValue();
    Type deepCopy();
}
