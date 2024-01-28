package Domain.Statements;

import Domain.ADT.ADTException;
import Domain.ADT.CustomStack;
import Domain.ProgramState;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.*;

public class ForkStatement implements Statement{
    Statement statement;

    public ForkStatement(Statement givenStatement) {
        if (givenStatement == null)
            throw new NullPointerException("ForkStatement(Statement statement): Given statement is null.");
        this.statement = givenStatement;
    }
    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        StackWrapper<Statement> newExecutionStack = new CustomStack<>();
        try {
            newExecutionStack.push(this.statement);
        } catch (ADTException e) {
            throw new StatementException("ForkStatement.execute(): " + e.getMessage());
        }
        DictionaryWrapper<String, Value> newSymbolsTable = state.getSymbolsTable().deepCopy();
        HeapInterface<Integer, Value> newHeap = state.getHeap();
        FileTableInterface newFileTable = state.getFileTable();
        ListWrapper<Value> newOutputList = state.getOutputList();
        SemaphoreTableInterface semaphoreTable = state.getSemaphoreTable();

        return new ProgramState(newExecutionStack, newSymbolsTable, newOutputList, newFileTable, newHeap, semaphoreTable);
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        try {
            this.statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        } catch (StatementException e) {
            throw new StatementException("ForkStatement.typeCheck(): " + e.getMessage());
        }
    }

    @Override
    public Statement deepCopy() {
        return new ForkStatement(this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + this.statement + ")";
    }
}
