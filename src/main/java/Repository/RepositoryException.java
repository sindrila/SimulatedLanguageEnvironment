package Repository;

import Domain.Utilities.GeneralException;

public class RepositoryException extends GeneralException {
    public RepositoryException() {
        super();
    }

    public RepositoryException(String message) {
        super(message);
    }
}
