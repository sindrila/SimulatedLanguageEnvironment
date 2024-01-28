package Domain.Expressions;

import Domain.ADT.ADTException;
import Domain.ADT.CustomDictionary;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.HeapInterface;

public class VariableExpression implements Expression {
    String id;

    public VariableExpression(String givenId) {
        if (givenId == null)
            throw new NullPointerException("VariableExpression: id cannot be null");
        this.id = givenId;
    }

    public String getId() {
        return id;
    }

    @Override
    public Value evaluate(DictionaryWrapper<String, Value> symbolsTable, HeapInterface<Integer, Value> heap) throws ExpressionException {
        try {
            return symbolsTable.get(id);
        } catch (ADTException e) {
            throw new ExpressionException("VariableExpression.evaluate(): variable with id '" + id + "' is not defined.");
        }
    }

    @Override
    public Type typeCheck(DictionaryWrapper<String, Type> typeEnvironment) throws ExpressionException {
        try {
            return typeEnvironment.get(id);
        } catch (ADTException e) {
            throw new ExpressionException("VariableExpression.typeCheck(): variable with id '" + id + "' is not defined.");
        }
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(this.id);
    }

    @Override
    public String toString() {
        return (this.id);
    }
}
