package Domain.Statements;

import Domain.ADT.ADTException;
import Domain.Expressions.Expression;
import Domain.Expressions.ExpressionException;
import Domain.ProgramState;
import Domain.Utilities.Types.StringType;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.FileTableInterface;

import java.io.IOException;

public class CloseReadFileStatement implements Statement{
    private final Expression expression;

    public CloseReadFileStatement(Expression expression) {
        if (expression == null)
            throw new NullPointerException("CloseReadFileStatement: expression is null");
        this.expression = expression;
    }

    private boolean isValueOfTypeString(Value givenValue) {
        return givenValue.getType().equals(new StringType());
    }

    private boolean isDefinedInFileTable(FileTableInterface fileTable, String filePath) {
        return fileTable.isDefined(filePath);
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        try
        {
            Value value = expression.evaluate(state.getSymbolsTable(), state.getHeap());
            if (!isValueOfTypeString(value))
                throw new StatementException("CloseReadFileStatement.execute(): Expression does not evaluate to a string.");
            String filePath = value.toString();
            if (!isDefinedInFileTable(state.getFileTable(), filePath))
                throw new StatementException("CloseReadFileStatement.execute(): File is not defined in the file table.");
            state.getFileTable().get(filePath).close();
            state.getFileTable().remove(filePath);
        }
        catch (ExpressionException | ADTException | IOException e)
        {
            throw new StatementException("CloseReadFileStatement.execute(): " + e.getMessage());
        }
        return null;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        Type expressionType;
        try {
            expressionType = expression.typeCheck(typeEnv);
        } catch (ExpressionException e) {
            throw new StatementException("CloseReadFileStatement.typeCheck: " + e.getMessage());
        }
        if (!expressionType.equals(new StringType()))
            throw new StatementException("CloseReadFileStatement.typeCheck: Expression is not of type string.");
        return typeEnv;
    }

    @Override
    public Statement deepCopy() {
        return new CloseReadFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "closeReadFile(" + this.expression + ")";
    }
}
