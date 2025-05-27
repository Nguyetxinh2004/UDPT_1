package space.example.commonservice.exception;

import space.example.commonservice.util.MessagesUtil;

public class BadCredentialsException extends RuntimeException {
    private final String message;

    public BadCredentialsException(String errorCode, Object... var2) {
        this.message = MessagesUtil.getMessage(errorCode, var2);
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
