package Domain.Statements;
import Domain.ADT.ADTException;
import Domain.Expressions.Expression;
import Domain.Expressions.ExpressionException;
import Domain.ProgramState;
import Domain.Utilities.Types.BoolType;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.BoolValue;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.StackWrapper;

public class IfStatement implements Statement {
    Expression expression;
    Statement thenStatement;
    Statement elseStatement;

    public IfStatement(Expression givenExpression, Statement givenThenStatement, Statement givenElseStatement) {
        if (givenExpression == null || givenThenStatement == null || givenElseStatement == null)
            throw new NullPointerException("IfStatement: given parameters are null.");
        this.expression = givenExpression;
        this.thenStatement = givenThenStatement;
        this.elseStatement = givenElseStatement;
    }

    public Expression getExpression() {
        return expression;
    }

    public Statement getThenStatement() {
        return thenStatement;
    }

    public Statement getElseStatement() {
        return elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {

        DictionaryWrapper<String, Value> symbolsTable = state.getSymbolsTable();
        StackWrapper<Statement> executionStack = state.getExecutionStack();
        Value condition;
        try {
            condition = expression.evaluate(symbolsTable, state.getHeap());
            if (!(condition.getType() instanceof BoolType))
                throw new StatementException("IfStatement.execute(): Conditional expression is not a boolean.");
            else {
                BoolValue boolCondition = (BoolValue) condition;
                if (boolCondition.getValue() == Boolean.TRUE)
                    executionStack.push(thenStatement);
                else
                    executionStack.push(elseStatement);
            }
        }
        catch (ExpressionException | ADTException e) {
            throw new StatementException("IfStatement.execute(), " + e.getMessage());
        }

        return null;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        try {
            Type expressionType = expression.typeCheck(typeEnv);
            if (expressionType.equals(new BoolType())) {
                thenStatement.typeCheck(typeEnv.deepCopy());
                elseStatement.typeCheck(typeEnv.deepCopy());
                return typeEnv;
            }
            else
                throw new StatementException("IfStatement.typeCheck(): Conditional expression is not a boolean.");
        } catch (ExpressionException e) {
            throw new StatementException("IfStatement.typeCheck(), " + e.getMessage());
        }
    }

    @Override
    public Statement deepCopy() {
        return new IfStatement(this.expression.deepCopy(), this.thenStatement.deepCopy(), this.elseStatement.deepCopy());
    }

    public String toString() {
        return "if (" + expression.toString() + ") then (" + thenStatement.toString() + ") else (" + elseStatement.toString() + ")";
    }
}

