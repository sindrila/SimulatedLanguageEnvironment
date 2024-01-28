package Domain.Statements;

import Domain.ProgramState;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Wrappers.DictionaryWrapper;

public class NoOperationStatement implements Statement {
    public NoOperationStatement() {

    }
    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        return state;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        return typeEnv;
    }

    @Override
    public Statement deepCopy() {
        return new NoOperationStatement();
    }

    @Override
    public String toString() {
        return "No operation statement.";
    }
}
