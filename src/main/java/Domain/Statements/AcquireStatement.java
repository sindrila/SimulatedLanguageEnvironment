package Domain.Statements;

import Domain.ADT.ADTException;
import Domain.ProgramState;
import Domain.Utilities.Types.IntType;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.IntValue;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.SemaphoreTableInterface;
import javafx.util.Pair;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStatement implements Statement {
    private final String variableName;
    private static final Lock lock = new ReentrantLock();

    public AcquireStatement(String variableName) {
        this.variableName = variableName;
    }


    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        lock.lock();
        DictionaryWrapper<String, Value> symbolsTable = state.getSymbolsTable();
        SemaphoreTableInterface semaphoreTable = state.getSemaphoreTable();
        try {
            if (!symbolsTable.isDefined(variableName))
                throw new StatementException("AcquireStatement.execute(): variable name is not defined in symbols table.");
            if (!symbolsTable.get(variableName).getType().equals(new IntType()))
                throw new StatementException("AcquireStatement.execute(): variable is not of type int.");
            IntValue key = (IntValue) symbolsTable.get(variableName);
            int foundIndex = key.getValue();
            if (!semaphoreTable.containsKey(foundIndex))
                throw new StatementException("AcquireStatement.execute(): index not a key in semaphore table.");
            Pair<Integer, List<Integer>> foundSemaphore = semaphoreTable.get(foundIndex);
            int lengthOfList = foundSemaphore.getValue().size();
            int semaphoreSize = foundSemaphore.getKey();
            if (semaphoreSize > lengthOfList) {
                if (!foundSemaphore.getValue().contains(state.getId())) {
                    foundSemaphore.getValue().add(state.getId());
                    semaphoreTable.update(foundIndex, new Pair<>(semaphoreSize, foundSemaphore.getValue()));
                }
            } else {
                state.getExecutionStack().push(this);
            }
        } catch (ADTException e) {
            throw new StatementException("AcquireStatement.execute(): " + e.getMessage());
        }
        finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        try {
            if (!typeEnv.get(variableName).equals(new IntType())) {
                throw new StatementException("AcquireStatement.typeCheck(): variable is not of type int.");
            }
            return typeEnv;
        } catch (ADTException e) {
            throw new StatementException("AcquireStatement.typeCheck(): " + e.getMessage());
        }
    }

    @Override
    public Statement deepCopy() {
        return new AcquireStatement(variableName);
    }

    @Override
    public String toString() {
        return String.format("acquire(%s)", variableName);
    }
}
