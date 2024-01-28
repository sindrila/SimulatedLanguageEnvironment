package Domain.Statements;

import Domain.ADT.ADTException;
import Domain.ADT.CustomDictionary;
import Domain.ADT.Heap;
import Domain.Expressions.Expression;
import Domain.Expressions.ExpressionException;
import Domain.ProgramState;
import Domain.Utilities.Types.ReferenceType;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.ReferenceValue;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.HeapInterface;

public class HeapAllocationStatement implements Statement{
    private final String variableName;
    private final Expression expression;

    public HeapAllocationStatement(String givenVariableName, Expression givenExpression)
    {
        this.variableName = givenVariableName;
        this.expression = givenExpression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        DictionaryWrapper<String, Value> symbolsTable = state.getSymbolsTable();
        HeapInterface<Integer, Value> heap = state.getHeap();
        try {
            Value variableValue = symbolsTable.get(this.variableName);
            Type variableType = variableValue.getType();
            if (!(variableType instanceof ReferenceType))
                throw new StatementException("HeapAllocationStatement.execute(): '" + this.variableName + "'is not a reference type.");
            ReferenceValue referenceVariable = (ReferenceValue)variableValue;
            Type referenceLocationType = referenceVariable.getLocationType();
            Value expressionValue = this.expression.evaluate(symbolsTable, state.getHeap());

            if (!referenceLocationType.equals(expressionValue.getType()))
                throw new StatementException("HeapAllocationStatement.execute(): Types of '" + this.variableName + "' and expression are not the same.");

            int newFreeLocation = heap.getNewFreeLocation();
            heap.put(newFreeLocation, expressionValue);
            symbolsTable.update(this.variableName, new ReferenceValue(newFreeLocation, referenceLocationType));
        } catch (ADTException | ExpressionException e) {
            throw new StatementException("HeapAllocationStatement.execute(): " + e.getMessage());
        }
        return null;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        try {
            Type variableType = typeEnv.get(this.variableName);
            Type expressionType = this.expression.typeCheck(typeEnv);
            if (!(variableType instanceof ReferenceType referenceVariable))
                throw new StatementException("HeapAllocationStatement.typeCheck(): '" + this.variableName + "'is not a reference type.");
            if (!referenceVariable.getInnerType().equals(expressionType))
                throw new StatementException("HeapAllocationStatement.typeCheck(): Types of '" + this.variableName + "' and expression are not the same.");
            return typeEnv;
        } catch (ADTException | ExpressionException e) {
            throw new StatementException("HeapAllocationStatement.typeCheck(): " + e.getMessage());
        }
    }

    @Override
    public Statement deepCopy() {
        return new HeapAllocationStatement(this.variableName, this.expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return "new(" + this.variableName + ", " + this.expression + ")";
    }
}
