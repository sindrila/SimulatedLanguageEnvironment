package Domain.Expressions;

import Domain.Utilities.GeneralException;

public class ExpressionException extends GeneralException {
    public ExpressionException() {super();}
    public ExpressionException(String message) {
        super("ExpressionException." +message);
    }
}
