package Domain.Expressions;

import Domain.ADT.ADTException;
import Domain.Utilities.Types.ReferenceType;
import Domain.Utilities.Types.Type;
import Domain.Utilities.Values.ReferenceValue;
import Domain.Utilities.Values.Value;
import Domain.Utilities.Wrappers.DictionaryWrapper;
import Domain.Utilities.Wrappers.HeapInterface;


public class ReadHeapExpression implements Expression {
    Expression innerExpression;

    public ReadHeapExpression(Expression givenInnerExpression) {
        this.innerExpression = givenInnerExpression;
    }

    @Override
    public Value evaluate(DictionaryWrapper<String, Value> symbolsTable, HeapInterface<Integer, Value> heap) throws ExpressionException {
        Value postEvaluationInnerExpressionValue = this.innerExpression.evaluate(symbolsTable, heap);
        if (!(postEvaluationInnerExpressionValue instanceof ReferenceValue heapReference))
            throw new ExpressionException("ReadHeapExpression.evaluate(): inner expression" + this.innerExpression.toString() + "does not evaluate to a ReferenceValue.");
        Integer heapReferenceAddress = heapReference.getAddress();
        try {
            return heap.get(heapReferenceAddress);
        } catch (ADTException e) {
            throw new ExpressionException("ReadHeapExpression.evaluate(): address" + heapReferenceAddress + "not allocated in the Heap");
        }
    }

    @Override
    public Type typeCheck(DictionaryWrapper<String, Type> typeEnvironment) throws ExpressionException {
        Type type = this.innerExpression.typeCheck(typeEnvironment);
        if (!(type instanceof ReferenceType referenceType))
            throw new ExpressionException("ReadHeapExpression.typeCheck(): inner expression" + this.innerExpression.toString() + "does not evaluate to a ReferenceType.");
        return referenceType.getInnerType();
    }

    @Override
    public Expression deepCopy() {
        return new ReadHeapExpression(this.innerExpression.deepCopy());
    }

    @Override
    public String toString() {
        return "readHeap(" + this.innerExpression + ")";
    }
}
