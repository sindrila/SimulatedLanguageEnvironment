package Domain.Utilities.Values;

import Domain.Utilities.Types.ReferenceType;
import Domain.Utilities.Types.Type;

public class ReferenceValue implements Value {
    private final int address;
    private final Type locationType;

    public ReferenceValue(int givenAddress, Type givenLocationType) {
        this.address = givenAddress;
        this.locationType = givenLocationType;
    }

    public int getAddress() {
        return this.address;
    }

    public Type getLocationType() {
        return this.locationType;
    }

    @Override
    public Type getType() {
        return new ReferenceType(this.locationType);
    }

    @Override
    public Value deepCopy() {
        return new ReferenceValue(this.address, this.locationType.deepCopy());
    }

    @Override
    public String toString()
    {
        return "(" + this.address + ", " + this.locationType + ")";
    }
}
