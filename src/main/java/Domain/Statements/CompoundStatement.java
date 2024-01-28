package Domain.Statements;

import Domain.ADT.ADTException;
import Domain.ProgramState;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.StackWrapper;

public class CompoundStatement implements Statement {
    Statement firstStatement;
    Statement secondStatement;


    public CompoundStatement(Statement givenFirstStatement, Statement givenSecondStatement) {
        if (givenFirstStatement == null || givenSecondStatement == null)
            throw new NullPointerException("CompoundStatement: null arguments given.");
        this.firstStatement = givenFirstStatement;
        this.secondStatement = givenSecondStatement;
    }

    public Statement getFirstStatement() {
        return firstStatement;
    }

    public Statement getSecondStatement() {
        return secondStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        StackWrapper<Statement> stack = state.getExecutionStack();
        try {
            stack.push(secondStatement);
            stack.push(firstStatement);
        } catch (ADTException e) {
            throw new StatementException("CompoundStatement.execute(), " + e.getMessage());
        }
        return null;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        return secondStatement.typeCheck(firstStatement.typeCheck(typeEnv));
    }

    @Override
    public Statement deepCopy() {
        return new CompoundStatement(this.firstStatement.deepCopy(), this.secondStatement.deepCopy());
    }

    public String toString() {
        return "(" + firstStatement.toString() + ";" + secondStatement.toString() + ")";
    }
}
