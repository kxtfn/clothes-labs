package ua.edu.sumdu.oop;

public class InvalidProductException extends IllegalArgumentException {

    public InvalidProductException(String message) {
        super(message);
    }

    public InvalidProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
