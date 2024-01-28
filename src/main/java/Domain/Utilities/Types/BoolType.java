package Domain.Utilities.Types;

import Domain.Utilities.Values.BoolValue;
import Domain.Utilities.Values.Value;

public class BoolType implements Type{
    public BoolType() {}
    @Override
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }


    @Override
    public String toString() {
        return "bool";
    }

    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }
}
