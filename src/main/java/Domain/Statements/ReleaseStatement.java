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

public class ReleaseStatement implements Statement {
    public final String variableName;
    private final static Lock lock = new ReentrantLock();

    public ReleaseStatement(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        lock.lock();
        DictionaryWrapper<String, Value> symbolsTable = state.getSymbolsTable();
        SemaphoreTableInterface semaphoreTable = state.getSemaphoreTable();
        if (!symbolsTable.isDefined(this.variableName))
            throw new StatementException("ReleaseStatement.execute(): variable not declared in symbols table.");
        try {
            if (!symbolsTable.get(variableName).getType().equals(new IntType()))
                throw new StatementException("ReleaseStatement.execute(): variable is not of type int.");
            IntValue key = (IntValue) symbolsTable.get(variableName);
            int foundIndex = key.getValue();
            if(!semaphoreTable.containsKey(foundIndex))
                throw new StatementException("ReleaseStatement.execute(): given index is not in the SemaphoreTable.");
            Pair<Integer, List<Integer>> foundSemaphore = semaphoreTable.get(foundIndex);
            int semaphoreLength = foundSemaphore.getKey();
            List<Integer> list = foundSemaphore.getValue();
            if (list.contains(state.getId())) {
                list.remove((Integer)state.getId());
            }
            semaphoreTable.update(foundIndex, new Pair<>(semaphoreLength, foundSemaphore.getValue()));
        } catch (ADTException e) {
            throw new StatementException("ReleaseStatement.execute(): " + e.getMessage());
        }
        finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        try {
            if (!typeEnv.get(variableName).equals(new IntType()))
                throw new StatementException("StatementException.typeCheck(): variable is not of type int.");
            return typeEnv;
        } catch (ADTException e) {
            throw new StatementException("StatementException.typeCheck(): " + e.getMessage());
        }
    }

    @Override
    public Statement deepCopy() {
        return new ReleaseStatement(variableName);
    }

    @Override
    public String toString() {
        return String.format("release(%s)", variableName);
    }
}
