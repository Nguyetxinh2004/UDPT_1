package space.example.commonservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import space.example.commonservice.util.MessagesUtil;

public class TokenVerificationException extends RuntimeException {
    private final String message;

    public TokenVerificationException(String errorCode, Object... var2) {
        this.message = MessagesUtil.getMessage(errorCode, var2);
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}