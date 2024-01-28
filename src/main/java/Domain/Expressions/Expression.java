package Domain.Expressions;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.HeapInterface;

public interface Expression {
    Value evaluate(DictionaryWrapper<String, Value> symbolsTable, HeapInterface<Integer, Value> heap) throws ExpressionException;
    Type typeCheck(DictionaryWrapper<String, Type> typeEnvironment) throws ExpressionException;
    Expression deepCopy();
}
