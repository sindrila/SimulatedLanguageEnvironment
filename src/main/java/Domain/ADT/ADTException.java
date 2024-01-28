package Domain.ADT;

import Domain.Utilities.GeneralException;

public class ADTException extends GeneralException {
    public ADTException() {super();}
    public ADTException(String message) {super("ADTException." + message);}
}
