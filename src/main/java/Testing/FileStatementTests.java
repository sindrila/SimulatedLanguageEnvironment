package Testing;

import Domain.ADT.*;
import Domain.Expressions.ValueExpression;
import Domain.ProgramState;
import Domain.Statements.*;
import Domain.Utilities.Types.IntType;
import Domain.Utilities.Values.IntValue;
import Domain.Utilities.Values.StringValue;
import Domain.Utilities.Wrappers.HeapInterface;

public class FileStatementTests {
    public void testTwoValidValuesInputFile() {
        ProgramState state = new ProgramState(new CustomStack<>(), new CustomDictionary<>(), new CustomList<>(), new FileTable(), new Heap<>(), new SemaphoreTable());

        StringValue filePath = new StringValue("./LogFiles/twoValidValuesInputFile.in");
        ValueExpression filePathExpression = new ValueExpression(filePath);

        try {
            OpenReadFileStatement OpenTestFileStatement = new OpenReadFileStatement(filePathExpression);
            OpenTestFileStatement.execute(state);
        } catch (StatementException e) {
            System.out.println(e.getMessage());
        }

        StringValue intVariableName = new StringValue("testIntVariable");
        VariableDeclarationStatement intVariableDeclarationStatement = new VariableDeclarationStatement(intVariableName.toString(), new IntType());
        try {
            intVariableDeclarationStatement.execute(state);

            ReadFileStatement readFileStatement = new ReadFileStatement(filePathExpression, intVariableName);
            readFileStatement.execute(state);
            IntValue readValue = (IntValue) state.getSymbolsTable().get(intVariableName.toString());
            assert(readValue.getValue() == 15);
            readFileStatement.execute(state);
            assert (state.getSymbolsTable().get(intVariableName.toString()).equals(new IntValue(50)));
        } catch (StatementException | ADTException e) {
            System.out.println(e.getMessage());
        }
        CloseReadFileStatement closeReadFileStatement = new CloseReadFileStatement(filePathExpression);
        try {
            closeReadFileStatement.execute(state);
        } catch (StatementException e) {
            System.out.println(e.getMessage());
        }
    }
    public void testEmptyInputFile() {
        ProgramState state = new ProgramState(new CustomStack<>(), new CustomDictionary<>(), new CustomList<>(), new FileTable(), new Heap<>(), new SemaphoreTable());

        StringValue filePath = new StringValue("./LogFiles/emptyInputFile.in");
        ValueExpression filePathExpression = new ValueExpression(filePath);

        try {
            OpenReadFileStatement OpenTestFileStatement = new OpenReadFileStatement(filePathExpression);
            OpenTestFileStatement.execute(state);
        } catch (StatementException e) {
            System.out.println(e.getMessage());
        }

        StringValue intVariableName = new StringValue("testIntVariable");
        VariableDeclarationStatement intVariableDeclarationStatement = new VariableDeclarationStatement(intVariableName.toString(), new IntType());
        try {
            intVariableDeclarationStatement.execute(state);

            ReadFileStatement readFileStatement = new ReadFileStatement(filePathExpression, intVariableName);
            readFileStatement.execute(state);
            assert (state.getSymbolsTable().get(intVariableName.toString()).equals(new IntValue(0)));

            readFileStatement.execute(state);
            assert (state.getSymbolsTable().get(intVariableName.toString()).equals(new IntValue(0)));
        } catch (StatementException | ADTException e) {
            System.out.println(e.getMessage());
        }
        CloseReadFileStatement closeReadFileStatement = new CloseReadFileStatement(filePathExpression);
        try {
            closeReadFileStatement.execute(state);
        } catch (StatementException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        FileStatementTests test = new FileStatementTests();
        test.testTwoValidValuesInputFile();
        test.testEmptyInputFile();
        System.out.println("FileStatementTests: All tests passed.");
    }
}
