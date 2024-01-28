package Domain.Statements;

import Domain.ADT.ADTException;
import Domain.ADT.SemaphoreTable;
import Domain.Expressions.Expression;
import Domain.Expressions.ExpressionException;
import Domain.ProgramState;
import Domain.Utilities.Types.IntType;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.IntValue;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.HeapInterface;
import Domain.Utilities.Wrappers.SemaphoreTableInterface;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CreateSemaphoreStatement implements Statement {
    private final String variableName;
    private final Expression expression;
    private static final Lock semaphoreLock = new ReentrantLock();

    public CreateSemaphoreStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        semaphoreLock.lock();
        DictionaryWrapper<String, Value> symbolsTable = state.getSymbolsTable();
        HeapInterface<Integer, Value> heap = state.getHeap();
        SemaphoreTableInterface semaphoreTable = state.getSemaphoreTable();
        try {
            IntValue expressionIntValue = (IntValue) (this.expression.evaluate(symbolsTable, heap));
            int number = expressionIntValue.getValue();
            int freeAddress = semaphoreTable.getFreeAddress();
            semaphoreTable.put(freeAddress, new Pair<>(number, new ArrayList<>()));
            if (symbolsTable.isDefined(variableName) && symbolsTable.get(variableName).getType().equals(new IntType()))
                symbolsTable.update(variableName, new IntValue(freeAddress));
            else
                throw new StatementException("CreateSemaphoreStatement.execute(): variable " + variableName + "is not defined or is not of type int.");

        } catch (ExpressionException | ADTException e) {
            throw new StatementException("CreateSemaphoreStatement.execute(): " + e.getMessage());
        }
        finally {
            semaphoreLock.unlock();
        }
        return null;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        try {
            if (!typeEnv.get(variableName).equals(new IntType())) {
                throw new StatementException("CreateSemaphore.typeCheck(): given variable is not of type int.");
            }
            if (!expression.typeCheck(typeEnv).equals(new IntType())) {
                throw new StatementException("CreateSemaphore.typeCheck(): expression is not of type int.");
            }
            return typeEnv;
        } catch (ADTException | ExpressionException e) {
            throw new StatementException("CreateSemaphore.typeCheck(): " + e.getMessage());
        }

    }

    @Override
    public Statement deepCopy() {
        return new CreateSemaphoreStatement(this.variableName, this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "createSemaphore(" + this.variableName + "), " + this.expression + ")";
    }
}
