package space.example.commonservice.exception;

import space.example.commonservice.util.MessagesUtil;

public class AlreadyExistedException extends RuntimeException {
    private final String message;

    public AlreadyExistedException(String errorCode, Object... var2) {
        this.message = MessagesUtil.getMessage(errorCode, var2);
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
