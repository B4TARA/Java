package by.baturel.spring.exception;

public class UserNameNotFoundException extends Exception {
    public UserNameNotFoundException(String message) {
        super(message);
    }
}
