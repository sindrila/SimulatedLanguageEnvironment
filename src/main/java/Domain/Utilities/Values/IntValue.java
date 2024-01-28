package Domain.Utilities.Values;

import Domain.Utilities.Types.IntType;
import Domain.Utilities.Types.Type;

import java.util.Objects;

public class IntValue implements Value{
    Integer value;
    public IntValue(Integer givenValue) {
        if (givenValue == null)
            throw new NullPointerException("IntValue: givenValue is null");
        this.value = givenValue;
    }

    public IntValue() {
        this.value = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntValue that = (IntValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    public Integer getValue() {
        return this.value;
    }
    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(this.value);
    }
}
