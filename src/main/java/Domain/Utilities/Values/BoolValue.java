package Domain.Utilities.Values;

import Domain.Utilities.Types.BoolType;
import Domain.Utilities.Types.Type;

import java.util.Objects;

public class BoolValue implements Value{
    Boolean value;
    public BoolValue(Boolean givenValue){
        if (givenValue == null)
            throw new NullPointerException("BoolValue: givenValue is null");
        this.value = givenValue;
    }
    @Override
    public Type getType() {
        return new BoolType();
    }

    public Boolean getValue() {
        return value;
    }

    public String toString(){
        return this.value.toString();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoolValue that = (BoolValue) o;
        return Objects.equals(value, that.value);
    }
}
