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
import Domain.Utilities.Wrappers.HeapInterface;
import Domain.Utilities.Wrappers.StackWrapper;

public class WhileStatement implements Statement {
    private final Expression expression;
    private final Statement statement;

    public WhileStatement(Expression givenExpression, Statement givenStatement) {
        this.expression = givenExpression;
        this.statement = givenStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        DictionaryWrapper<String, Value> symbolsTable = state.getSymbolsTable();
        HeapInterface<Integer, Value> heap = state.getHeap();
        StackWrapper<Statement> executionStack = state.getExecutionStack();
        try {
            Value evaluatedExpression = expression.evaluate(symbolsTable, heap);
            if (!(evaluatedExpression instanceof BoolValue condition)) {
                throw new StatementException("WhileStatement.execute(): given expression " + evaluatedExpression + " does not evaluate to a BoolValue.");
            }
            if (condition.getValue()) {
                executionStack.push(this.deepCopy());
                executionStack.push(statement);
            }
        } catch (ExpressionException | ADTException e) {
            throw new StatementException("WhileStatement.execute(): " + e.getMessage());
        }
        return null;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        try {
            Type expressionType = this.expression.typeCheck(typeEnv);
            if (!(expressionType instanceof BoolType))
                throw new StatementException("WhileStatement.typeCheck(): Conditional expression is not a boolean.");
            this.statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        } catch (ExpressionException e) {
            throw new StatementException("WhileStatement.typeCheck(): " + e.getMessage());
        }
    }

    @Override
    public Statement deepCopy() {
        return new WhileStatement(this.expression.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return "while (" + this.expression + ") {" + this.statement + "}";
    }
}
