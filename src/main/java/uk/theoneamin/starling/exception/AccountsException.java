package uk.theoneamin.starling.exception;

public class AccountsException extends RuntimeException {
    public AccountsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountsException(String message) {
        super(message);
    }
}
