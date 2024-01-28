package Domain.Statements;

import Domain.ADT.ADTException;
import Domain.Expressions.Expression;
import Domain.Expressions.ExpressionException;
import Domain.ProgramState;
import Domain.Utilities.Types.IntType;
import Domain.Utilities.Types.StringType;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.IntValue;
import Domain.Utilities.Values.StringValue;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements Statement {
    private final Expression fileTableKeyExpression;
    private final StringValue variableStringValue;

    public ReadFileStatement(Expression givenFileTableKeyExpression, StringValue givenVariableStringValue) {
        this.fileTableKeyExpression = givenFileTableKeyExpression;
        this.variableStringValue = givenVariableStringValue;
    }

    private boolean isDefinedInSymbolsTable(ProgramState state, String variableName) {
        return state.getSymbolsTable().isDefined(variableName);
    }

    private boolean isValueOfTypeInt(Value givenValue) {
        return givenValue.getType().equals(new IntType());
    }

    private boolean isValueOfTypeString(Value givenValue) {
        return givenValue.getType().equals(new StringType());
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        DictionaryWrapper<String, Value> symbolsTable = state.getSymbolsTable();
        String variableName = this.variableStringValue.toString();
        try {
            if (!isDefinedInSymbolsTable(state, variableName)) {
                throw new StatementException("ReadFileStatement.execute(): Variable is not defined in the symbols table.");
            }
            Value valueOfVariable = symbolsTable.get(variableName);

            if (!isValueOfTypeInt(valueOfVariable)) {
                throw new StatementException("ReadFileStatement.execute(): Variable is not of type IntValue.");
            }
            Value temporaryValue = this.fileTableKeyExpression.evaluate(symbolsTable, state.getHeap());

            if (!isValueOfTypeString(temporaryValue)) {
                throw new StatementException("ReadFileStatement.execute(): Expression does not evaluate to a StringValue.");
            }
            StringValue filePath = (StringValue) temporaryValue;

            BufferedReader fileBufferedReader = state.getFileTable().get(filePath.toString());
            String line = fileBufferedReader.readLine();
            if (line == null)
                symbolsTable.update(variableName, new IntValue(0));
            else
                symbolsTable.update(variableName, new IntValue(Integer.parseInt(line)));
        } catch (ADTException | ExpressionException | IOException e) {
            throw new StatementException("ReadFileStatement.execute(): " + e.getMessage());
        }
        return null;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        try {
            Type variableType = typeEnv.get(this.variableStringValue.toString());
            Type expressionType = this.fileTableKeyExpression.typeCheck(typeEnv);
            if (!variableType.equals(new IntType())) {
                throw new StatementException("ReadFileStatement.typeCheck(): Variable is not of type IntType.");
            }
            if (!expressionType.equals(new StringType())) {
                throw new StatementException("ReadFileStatement.typeCheck(): Expression is not of type StringType.");
            }
            return typeEnv;
        } catch (ADTException | ExpressionException e) {
            throw new StatementException("ReadFileStatement.typeCheck(): " + e.getMessage());
        }
    }

    @Override
    public Statement deepCopy() {
        return new ReadFileStatement(this.fileTableKeyExpression.deepCopy(), this.variableStringValue);
    }

    @Override
    public String toString()
    {
        return "ReadFileStatement(path=" + this.fileTableKeyExpression + ", variable=" + this.variableStringValue;
    }
}
