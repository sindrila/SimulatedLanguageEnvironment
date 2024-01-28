package Domain.Utilities.Types;

import Domain.Utilities.Values.StringValue;
import Domain.Utilities.Values.Value;

public class StringType implements Type {
    public StringType() {
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof StringType;
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }
}
