package Domain.Utilities.Values;

import Domain.Utilities.Types.StringType;
import Domain.Utilities.Types.Type;

import java.util.Objects;

public class StringValue implements Value{
    private final String value;

    public StringValue(String givenValue) {
        if (givenValue == null)
            throw new NullPointerException("StringValue: givenValue is null");
        this.value = givenValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringValue that = (StringValue) o;
        return Objects.equals(value, that.value);
    }
    public String getValue() {
        return this.value;
    }


    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public Value deepCopy() {
        return new StringValue(this.value);
    }
}
