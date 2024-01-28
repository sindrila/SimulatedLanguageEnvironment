package Domain.Expressions;
import Domain.Utilities.Types.BoolType;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.BoolValue;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.HeapInterface;

public class LogicalExpression implements Expression {
    private final Expression expression1;
    private final Expression expression2;
    LogicalOperation logicalOperation;

    public LogicalExpression(Expression givenExpression1, Expression givenExpression2, LogicalOperation givenLogicalOperation)  {
        if (givenExpression1 == null || givenExpression2 == null || givenLogicalOperation == null)
            throw new NullPointerException("LogicalExpression: Null arguments given");

        this.expression1 = givenExpression1;
        this.expression2 = givenExpression2;
        this.logicalOperation = givenLogicalOperation;
    }

    private void checkBoolTypeOfValue(Value givenValue, String errorMessage) throws ExpressionException {
        if (!givenValue.getType().equals(new BoolType()))
            throw new ExpressionException(errorMessage + " Got: " + givenValue.getType());
    }

    @Override
    public Value evaluate(DictionaryWrapper<String, Value> symbolsTable, HeapInterface<Integer, Value> heap) throws ExpressionException {
        Value value1 = expression1.evaluate(symbolsTable, heap);
        checkBoolTypeOfValue(value1, "LogicalExpression: Expected a boolean for the first operand.");
        Value value2 = expression2.evaluate(symbolsTable, heap);
        checkBoolTypeOfValue(value2, "LogicalExpression: Expected a boolean for the second operand.");
        BoolValue boolValue1 = (BoolValue) value1;
        BoolValue boolValue2 = (BoolValue) value2;

        Boolean bool1 = boolValue1.getValue();
        Boolean bool2 = boolValue2.getValue();
        return switch (this.logicalOperation) {
            case AND -> new BoolValue(bool1 && bool2);
            case OR -> new BoolValue(bool1 || bool2);
            default -> throw new ExpressionException("LogicalExpression: Invalid operand.");
        };
    }

    @Override
    public Type typeCheck(DictionaryWrapper<String, Type> typeEnvironment) throws ExpressionException {
        Type type1 = expression1.typeCheck(typeEnvironment);
        Type type2 = expression2.typeCheck(typeEnvironment);
        if (!type1.equals(new BoolType()))
            throw new ExpressionException("LogicalExpression: First operand is not a boolean.");
        if (!type2.equals(new BoolType()))
            throw new ExpressionException("LogicalExpression: Second operand is not a boolean.");
        return new BoolType();
    }

    @Override
    public Expression deepCopy() {
        return new LogicalExpression(this.expression1.deepCopy(), this.expression2.deepCopy(), this.logicalOperation);
    }

    @Override
    public String toString() {
        return expression1+ " " + logicalOperation.toString() + " " + expression2;
    }
}
