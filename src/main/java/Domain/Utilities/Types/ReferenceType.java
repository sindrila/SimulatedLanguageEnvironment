package Domain.Utilities.Types;
import Domain.Utilities.Values.ReferenceValue;
import Domain.Utilities.Values.Value;

public class ReferenceType implements Type {
    Type innerType;

    public ReferenceType(Type givenInnerType) {
        this.innerType = givenInnerType;
    }

    public Type getInnerType() {
        return this.innerType;
    }

    @Override
    public boolean equals(Object another)
    {
        if (another instanceof ReferenceType)
            return this.innerType.equals(((ReferenceType) another).getInnerType());
        return false;
    }

    @Override
    public String toString() {
        return "Ref(" + this.innerType.toString() + ")";
    }

    @Override
    public Value defaultValue() {
        return new ReferenceValue(0, innerType);
    }

    @Override
    public Type deepCopy() {
        return new ReferenceType(this.innerType.deepCopy());
    }
}
