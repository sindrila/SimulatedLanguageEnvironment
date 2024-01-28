package Domain.Expressions;
import Domain.Utilities.Types.IntType;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.IntValue;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.HeapInterface;

public class ArithmeticExpression implements Expression {
    private final Expression expression1;
    private final Expression expression2;
    private final ArithmeticOperation arithmeticOperation;

    public ArithmeticExpression(Expression givenExpression1, Expression givenExpression2, ArithmeticOperation givenArithmeticOperation) {
        if (givenExpression1 == null || givenExpression2 == null || givenArithmeticOperation == null)
            throw new NullPointerException("ArithmeticExpression: Null arguments given.");
        this.expression1 = givenExpression1;
        this.expression2 = givenExpression2;
        this.arithmeticOperation = givenArithmeticOperation;
    }
    public Expression getExpression1() {
        return expression1;
    }

    public Expression getExpression2() {
        return expression2;
    }

    public ArithmeticOperation getArithmeticOperation() {
        return arithmeticOperation;
    }


    private void checkIntTypeOfValue(Value givenValue, String errorMessage) throws ExpressionException {
        if (!givenValue.getType().equals(new IntType()))
            throw new ExpressionException(errorMessage + " Got: " + givenValue.getType());
    }

    @Override
    public Value evaluate(DictionaryWrapper<String, Value> symbolsTable, HeapInterface<Integer, Value> heap) throws ExpressionException {
        Value value1 = expression1.evaluate(symbolsTable, heap);
        checkIntTypeOfValue(value1, "ArithmeticExpression: Expected an integer for the first operand.");
        Value value2 = expression2.evaluate(symbolsTable, heap);
        checkIntTypeOfValue(value2, "ArithmeticExpression: Expected an integer for the second operand.");

        IntValue integer1 = (IntValue) value1;
        IntValue integer2 = (IntValue) value2;

        int number1 = integer1.getValue();
        int number2 = integer2.getValue();

        return switch (arithmeticOperation) {
            case ADDITION -> new IntValue(number1 + number2);
            case SUBTRACTION -> new IntValue(number1 - number2);
            case MULTIPLICATION -> new IntValue(number1 * number2);
            case DIVISION -> {
                if (number2 == 0)
                    throw new ExpressionException("ArithmeticExpression: Division by zero");
                yield new IntValue(number1 / number2);
            }
            default ->
                    throw new ExpressionException("ArithmeticExpression: Operation not specified for this type of expression."); // needed if more values are added to the enum and not handled
        };
    }

    @Override
    public Type typeCheck(DictionaryWrapper<String, Type> typeEnvironment) throws ExpressionException {
        Type type1 = expression1.typeCheck(typeEnvironment);
        Type type2 = expression2.typeCheck(typeEnvironment);
        if (!type1.equals(new IntType()))
            throw new ExpressionException("ArithmeticExpression.typeCheck(): first operand is not an integer");
        if (!type2.equals(new IntType()))
            throw new ExpressionException("ArithmeticExpression.typeCheck(): second operand is not an integer");
        return new IntType();
    }

    @Override
    public Expression deepCopy() {
        return new ArithmeticExpression(this.expression1.deepCopy(), this.expression2.deepCopy(), this.arithmeticOperation);
    }

    @Override
    public String toString() {
        return expression1 + arithmeticOperation.toString() + expression2;
    }
}
