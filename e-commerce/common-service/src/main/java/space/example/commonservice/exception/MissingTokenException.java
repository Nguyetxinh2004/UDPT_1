package space.example.commonservice.exception;

import space.example.commonservice.util.MessagesUtil;

public class MissingTokenException extends RuntimeException {
    private final String message;

    public MissingTokenException(String errorCode, Object... var2) {
        this.message = MessagesUtil.getMessage(errorCode, var2);
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
