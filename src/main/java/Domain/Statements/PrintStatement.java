package Domain.Statements;
import Domain.Expressions.Expression;
import Domain.Expressions.ExpressionException;
import Domain.ProgramState;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Wrappers.DictionaryWrapper;

public class PrintStatement implements Statement {
    Expression expression;

    public PrintStatement(Expression givenExpression) {
        if (givenExpression == null)
            throw new NullPointerException("PrintStatement: givenExpression is null");
        this.expression = givenExpression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        try {
            state.getOutputList().add(this.expression.evaluate(state.getSymbolsTable(), state.getHeap()));
        } catch (ExpressionException e) {
            throw new StatementException("PrintStatement.execute(): " + e.getMessage());
        }
        return null;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        try {
            this.expression.typeCheck(typeEnv);
            return typeEnv;
        } catch (ExpressionException e) {
            throw new StatementException("PrintStatement.typeCheck(): " + e.getMessage());
        }
    }

    @Override
    public Statement deepCopy() {
        return new PrintStatement(this.expression.deepCopy());
    }

    public String toString() {
        return "print(" + this.expression.toString() + ")";
    }
}
