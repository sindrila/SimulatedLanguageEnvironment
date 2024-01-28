package Domain.Expressions;
import Domain.Utilities.Types.BoolType;
import Domain.Utilities.Types.IntType;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.BoolValue;
import Domain.Utilities.Values.IntValue;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.HeapInterface;

public class RelationalExpression implements Expression{
    Expression expression1;
    Expression expression2;

    RelationalOperation relationalOperation;

    public RelationalExpression(Expression givenExpression1, Expression givenExpression2, RelationalOperation givenRelationalOperation) {
        if (givenExpression1 == null || givenExpression2 == null || givenRelationalOperation == null)
            throw new NullPointerException("RelationalExpression: Null arguments given");

        this.expression1 = givenExpression1;
        this.expression2 = givenExpression2;
        this.relationalOperation = givenRelationalOperation;
    }

    private void checkIntTypeOfValue(Value givenValue, String errorMessage) throws ExpressionException {
        if (!givenValue.getType().equals(new IntType()))
            throw new ExpressionException(errorMessage + " Got: " + givenValue.getType());
    }

    @Override
    public Value evaluate(DictionaryWrapper<String, Value> symbolsTable, HeapInterface<Integer, Value> heap) throws ExpressionException {
        Value value1 = expression1.evaluate(symbolsTable, heap);
        checkIntTypeOfValue(value1, "RelationalExpression: Expected an integer for the first operand.");
        Value value2 = expression2.evaluate(symbolsTable, heap);
        checkIntTypeOfValue(value2, "RelationalExpression: Expected an integer for the second operand.");

        int number1 = ((IntValue) value1).getValue();
        int number2 = ((IntValue) value2).getValue();

        return switch (this.relationalOperation) {
            case EQUAL -> new BoolValue(number1 == number2);
            case NOT_EQUAL -> new BoolValue(number1 != number2);
            case LESS_THAN -> new BoolValue(number1 < number2);
            case LESS_THAN_OR_EQUAL -> new BoolValue(number1 <= number2);
            case GREATER_THAN -> new BoolValue(number1 > number2);
            case GREATER_THAN_OR_EQUAL -> new BoolValue(number1 >= number2);
            default -> throw new ExpressionException("RelationalExpression: Invalid operand.");
        };
    }

    @Override
    public Type typeCheck(DictionaryWrapper<String, Type> typeEnvironment) throws ExpressionException {
        Type type1 = expression1.typeCheck(typeEnvironment);
        Type type2 = expression2.typeCheck(typeEnvironment);
        if (!type1.equals(new IntType()))
            throw new ExpressionException("RelationalExpression: First operand is not an integer.");
        if (!type2.equals(new IntType()))
            throw new ExpressionException("RelationalExpression: Second operand is not an integer.");
        return new BoolType();
    }

    @Override
    public Expression deepCopy() {
        return new RelationalExpression(this.expression1.deepCopy(), this.expression2.deepCopy(), this.relationalOperation);
    }

    @Override
    public String toString() {
        return expression1.toString() + relationalOperation.toString() + expression2.toString();
    }
}
