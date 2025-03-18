package ldg.progettoispw.exception;

import java.io.Serial;

public class DBException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public DBException (String s)
    {
        super(s);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
