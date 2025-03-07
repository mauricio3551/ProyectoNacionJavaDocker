package sube.interviews.mareoenvios.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Exception e) {
        super(message, e.getCause());
    }
}
