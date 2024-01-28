package Domain.Utilities.Types;

import Domain.Utilities.Values.IntValue;
import Domain.Utilities.Values.Value;

public class IntType implements Type {
    public IntType() {}
    @Override
    public boolean equals(Object another) {
        return another instanceof IntType;
    }
    @Override
    public String toString()
    {
        return "int";
    }
    public Value defaultValue() {
        return new IntValue(0);
}

    @Override
    public Type deepCopy() {
        return new IntType();
    }
}
