package Domain.Statements;

import Domain.ADT.ADTException;
import Domain.ADT.CustomList;
import Domain.Expressions.Expression;
import Domain.Expressions.ExpressionException;
import Domain.ProgramState;
import Domain.Utilities.Types.ReferenceType;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.ReferenceValue;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.HeapInterface;

public class HeapWritingStatement implements Statement {
    String variableName;
    Expression expression;

    public HeapWritingStatement(String givenVariableName, Expression givenExpression) {
        this.variableName = givenVariableName;
        this.expression = givenExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        DictionaryWrapper<String, Value> symbolsTable = state.getSymbolsTable();
        HeapInterface<Integer, Value> heap = state.getHeap();
        try {
            Value variableValue = symbolsTable.get(variableName);
            if (!(variableValue.getType() instanceof ReferenceType))
                throw new StatementException("HeapWritingStatement.execute(): variable " + this.variableName + " is not ReferenceType");
            ReferenceValue referenceValue = (ReferenceValue) variableValue;
            if (!(heap.isDefined(referenceValue.getAddress()))) {
                throw new StatementException("HeapWritingStatement.execute(): variable " + this.variableName + " is not allocated on the heap");
            }
            Value expressionValue = this.expression.evaluate(symbolsTable, heap);

            if (!(expressionValue.getType().equals(referenceValue.getLocationType()))) {
                throw new StatementException("HeapWritingStatement.execute(): expression " + this.expression + " does not evaluate to the same type as variable " + this.variableName);
            }
            heap.update(referenceValue.getAddress(), expressionValue);
        } catch (ADTException | ExpressionException e) {
            throw new StatementException("HeapWritingStatement.execute(): " + e.getMessage());
        }
        return null;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        try {
            Type variableType = typeEnv.get(this.variableName);
            Type expressionType = this.expression.typeCheck(typeEnv);
            if (!(variableType instanceof ReferenceType referenceType))
                throw new StatementException("HeapWritingStatement.typeCheck(): variable " + this.variableName + " is not ReferenceType");
            if (!(referenceType.getInnerType().equals(expressionType)))
                throw new StatementException("HeapWritingStatement.typeCheck(): expression " + this.expression + " does not evaluate to the same type as variable " + this.variableName);
            return typeEnv;
        } catch (ADTException | ExpressionException e) {
            throw new StatementException("HeapWritingStatement.typeCheck(): " + e.getMessage());
        }
    }

    @Override
    public Statement deepCopy() {
        return new HeapWritingStatement(this.variableName, this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "writeHeap(" + this.variableName + ", " + this.expression + ")";
    }
}
