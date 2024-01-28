package Domain.Statements;

import Domain.ADT.CustomDictionary;
import Domain.Expressions.Expression;
import Domain.Expressions.ExpressionException;
import Domain.ProgramState;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.HeapInterface;

public class SwitchStatement implements Statement {
    Expression conditionExpression;
    Expression expression1;
    Expression expression2;
    Statement statement1;
    Statement statement2;
    Statement statement3;

    public SwitchStatement(Expression givenConditionExpression, Expression givenExpression1, Expression givenExpression2, Statement givenStatement1, Statement givenStatement2, Statement givenStatement3) {
        this.conditionExpression = givenConditionExpression;
        this.expression1 = givenExpression1;
        this.expression2 = givenExpression2;
        this.statement1 = givenStatement1;
        this.statement2 = givenStatement2;
        this.statement3 = givenStatement3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        try {
            DictionaryWrapper<String, Value> symbolsTable = state.getSymbolsTable();
            HeapInterface<Integer, Value> heap = state.getHeap();
            Value conditionExpressionValue = this.conditionExpression.evaluate(symbolsTable, heap);
            if (conditionExpressionValue.equals(this.expression1.evaluate(symbolsTable, heap))) {
                this.statement1.execute(state);
            } else if (conditionExpressionValue.equals(this.expression2.evaluate(symbolsTable, heap))) {
                this.statement2.execute(state);
            } else {
                this.statement3.execute(state);
            }
        } catch (ExpressionException e) {
            throw new StatementException("SwitchStatement.typeCheck(): " + e.getMessage());
        }
        return null;
    }

    @Override
    public DictionaryWrapper<String, Type> typeCheck(DictionaryWrapper<String, Type> typeEnv) throws StatementException {
        try {
            Type conditionExpressionType = this.conditionExpression.typeCheck(typeEnv);
            Type expression1Type = this.expression1.typeCheck(typeEnv);
            Type expression2Type = this.expression2.typeCheck(typeEnv);
            if (!conditionExpressionType.equals(expression1Type))
                throw new StatementException("SwitchStatement.typeCheck(): Expression is of wrong type.");
            if (!expression1Type.equals(expression2Type))
                throw new StatementException("SwitchStatement.typeCheck(): Expression is of wrong type.");
            this.statement1.typeCheck(typeEnv);
            this.statement2.typeCheck(typeEnv);
            this.statement3.typeCheck(typeEnv);
        } catch (ExpressionException e) {
            throw new StatementException("SwitchStatement.typeCheck(): " + e.getMessage());
        }
        return typeEnv;
    }

    @Override
    public Statement deepCopy() {
        return new SwitchStatement(this.conditionExpression.deepCopy(), this.expression1.deepCopy(), this.expression2.deepCopy(), this.statement1.deepCopy(), this.statement2.deepCopy(), this.statement3.deepCopy());
    }

    @Override
    public String toString() {
        return "switch (" + this.conditionExpression + ")\n" + "\t(case (" + this.expression1 + "): " + this.statement1
                + ")\n\t(case" + this.expression2 + ")\n\t(default: " + this.statement3 + ")\n";
    }
}
