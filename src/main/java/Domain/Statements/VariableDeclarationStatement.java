package Domain.Statements;
import Domain.ADT.ADTException;
import Domain.ProgramState;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;


public class VariableDeclarationStatement implements Statement {
    String name;
    Type type;

    public VariableDeclarationStatement(String givenName, Type givenType) {
        if (givenName == null || givenType == null)
            throw new NullPointerException("VariableDeclarationStatement: Null arguments given.");
        this.name = givenName;
        this.type = givenType;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        DictionaryWrapper<String, Value> symbolsTable = state.getSymbolsTable();
        if (symbolsTable.isDefined(this.name))
            throw new StatementException("VariableDeclarationStatement.execute(): Variable is already declared.");
        else {
            try {
                symbolsTable.put(name, this.type.defaultValue());
            } catch (ADTException e) {
                throw new StatementException("VariableDeclarationStatement.execute(): " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        try {
            typeEnv.put(this.name, this.type);
            return typeEnv;
        } catch (ADTException e) {
            throw new StatementException("VariableDeclarationStatement.typeCheck(): " + e.getMessage());
        }
    }

    @Override
    public Statement deepCopy() {
        return new VariableDeclarationStatement(this.name, this.type.deepCopy());
    }

    public String toString() {
        return this.type.toString() + ' ' + this.name;
    }
}
