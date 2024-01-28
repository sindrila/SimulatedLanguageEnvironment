package Domain.Statements;
import Domain.ProgramState;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Wrappers.DictionaryWrapper;

public interface Statement {
    ProgramState execute(ProgramState state) throws StatementException;
    DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException;
    Statement deepCopy();
}
