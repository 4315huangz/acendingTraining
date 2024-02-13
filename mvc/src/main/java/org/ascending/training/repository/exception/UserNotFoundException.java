package org.ascending.training.repository.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {super();}

    public UserNotFoundException(String message) {super(message);}

}
