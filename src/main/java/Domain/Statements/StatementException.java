package Domain.Statements;

import Domain.Utilities.GeneralException;

public class StatementException extends GeneralException {
    public StatementException() {super();}
    public StatementException(String message) {super("StatementException." + message);}
}
