package Domain.Statements;

import Domain.ADT.ADTException;
import Domain.ADT.FileTable;
import Domain.Expressions.Expression;
import Domain.Expressions.ExpressionException;
import Domain.ProgramState;
import Domain.Utilities.Types.StringType;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.StringValue;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.FileTableInterface;

import java.io.*;

public class OpenReadFileStatement implements Statement {
    private final Expression expression;

    public OpenReadFileStatement(Expression expression) {
        if (expression == null) {
            throw new NullPointerException("Expression cannot be null");
        }
        this.expression = expression;
    }

    private boolean isValueOfTypeString(Value givenValue) {
        return givenValue.getType().equals(new StringType());
    }

    private boolean isAlreadyDefinedInFileTable(FileTableInterface fileTable, String filePath) {
        return fileTable.isDefined(filePath);
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        try {
            Value value = this.expression.evaluate(state.getSymbolsTable(), state.getHeap());
            if (!isValueOfTypeString(value)) {
                throw new StatementException("OpenReadFileStatement.execute(): Expression does not evaluate to a string.");
            }

            StringValue stringValue = (StringValue) value;
            String filePath = stringValue.getValue();
            FileTableInterface currentFileTable = state.getFileTable();

            if (isAlreadyDefinedInFileTable(currentFileTable, filePath)) {
                throw new StatementException("OpenReadFileStatement.execute(): File is already defined in the file table.");
            }
            BufferedReader fileBuffer = new BufferedReader(new FileReader(filePath));
            currentFileTable.put(filePath, fileBuffer);
        } catch (ExpressionException | IOException | ADTException e) {
            throw new StatementException("OpenReadFileStatement.execute(): " + e.getMessage());
        }
        return null;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        try {
            Type expressionType = this.expression.typeCheck(typeEnv);
            if (!expressionType.equals(new StringType())) {
                throw new StatementException("OpenReadFileStatement.typeCheck(): Expression does not evaluate to a string.");
            }
            return typeEnv;
        } catch (ExpressionException e) {
            throw new StatementException("OpenReadFileStatement.typeCheck(): " + e.getMessage());
        }
    }


    @Override
    public Statement deepCopy() {
        return new OpenReadFileStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "OpenReadFile(" + this.expression + ")";
    }
}
