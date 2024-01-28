package Domain.Expressions;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.HeapInterface;

public class ValueExpression implements Expression{
    private final Value value;

    public ValueExpression(Value givenValue) {
        if (givenValue == null)
            throw new NullPointerException("ValueExpression: value is null");
        this.value = givenValue;
    }

    @Override
    public Value evaluate(DictionaryWrapper<String, Value> symbolsTable, HeapInterface<Integer, Value> heap) throws ExpressionException {
        return value;
    }

    @Override
    public Type typeCheck(DictionaryWrapper<String, Type> typeEnvironment) throws ExpressionException {
        return value.getType();
    }

    public Value getValue() {
        return value;
    }

    @Override
    public Expression deepCopy() {
        return new ValueExpression(this.value.deepCopy());
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
