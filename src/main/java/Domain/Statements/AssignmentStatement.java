package Domain.Statements;

import Domain.ADT.ADTException;
import Domain.Expressions.Expression;
import Domain.Expressions.ExpressionException;
import Domain.ProgramState;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;

public class AssignmentStatement implements Statement {
    String id;
    Expression expression;

    public AssignmentStatement(String givenId, Expression givenExpression) {
        if (givenId == null || givenExpression == null)
            throw new NullPointerException("AssignmentStatement: Null arguments given.");
        this.id = givenId;
        this.expression = givenExpression;
    }

    public String getId() {
        return id;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        DictionaryWrapper<String, Value> symbolsTable = state.getSymbolsTable();

        try {
            Value value = expression.evaluate(symbolsTable, state.getHeap());
            Type typeId = (symbolsTable.get(id)).getType();
            if (value.getType().equals(typeId))
                symbolsTable.update(id, value);
            else
                throw new StatementException("AssignmentStatement: Declared type of variable " + id + " and type of the assigned expression do not match.");
        } catch (ExpressionException | ADTException ee) {
            throw new StatementException("AssignmentStatement.execute(): " + ee.getMessage());
        }
        return null;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        try {
            Type typeVariable = typeEnv.get(id);
            Type typeExpression = expression.typeCheck(typeEnv);
            if (typeVariable.equals(typeExpression))
                return typeEnv;
            else
                throw new StatementException("AssignmentStatement.typeCheck(): Right hand side and left hand side have different types.");
        } catch (ADTException | ExpressionException e) {
            throw new StatementException("AssignmentStatement.typeCheck(): " + e.getMessage());
        }
    }

    @Override
    public Statement deepCopy() {
        return new AssignmentStatement(this.id, this.expression.deepCopy());
    }

    public String toString() {
        return id + "=" + expression.toString();
    }
}
