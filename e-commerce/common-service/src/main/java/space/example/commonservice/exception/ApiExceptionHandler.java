package space.example.commonservice.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import space.example.commonservice.dto.response.ErrorResp;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    private static final String ERROR_LOG_FORMAT = "Error: URI: {}, Message: {}, Method: {}";
    private static final String INVALID_REQUEST_INFORMATION_MESSAGE = "Request information is not valid";
    private static final String METHOD_NOT_ALLOWED_MESSAGE_FORMAT =
            "Method %s is not allowed for this endpoint. Supported methods are %s";


    private static final String DEFAULT_ERROR_MESSAGE = "An unexpected error occurred. Please try again later.";


    @ExceptionHandler(Exception.class) // for all other exceptions debug
    public ResponseEntity<ErrorResp> handleGeneralException(Exception ex, WebRequest request) {
        ex.getStackTrace();
        ex.printStackTrace();

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                DEFAULT_ERROR_MESSAGE, null, ex, request);
    }

    @ExceptionHandler(TokenVerificationException.class)
    public ResponseEntity<ErrorResp> handleTokenVerificationException(TokenVerificationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = ex.getMessage();

        return buildErrorResponse(status, message, null, ex, request);
    }

    @ExceptionHandler(OrderUpdateNotAllowedException.class)
    public ResponseEntity<ErrorResp> handleOrderUpdateNotAllowedException(OrderUpdateNotAllowedException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String message = ex.getMessage();

        return buildErrorResponse(status, message, null, ex, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResp> handleNotFoundException(NotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = ex.getMessage();

        return buildErrorResponse(status, message, null, ex, request);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResp> handleFeignException(FeignException ex, WebRequest request) {
        HttpStatus status = HttpStatus.valueOf(ex.status());
        ErrorResp errorResp = parseFeignErrorBody(ex);

        return new ResponseEntity<>(errorResp, status);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResp> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = ex.getMessage();

        return buildErrorResponse(status, message, null, ex, request);
    }

    @ExceptionHandler(AlreadyExistedException.class)
    public ResponseEntity<ErrorResp> handleAlreadyExistedException(AlreadyExistedException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String message = ex.getMessage();

        return buildErrorResponse(status, message, null, ex, request);
    }


    @ExceptionHandler(MissingTokenException.class)
    public ResponseEntity<ErrorResp> handleMissingTokenException(MissingTokenException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = ex.getMessage();

        return buildErrorResponse(status, message, null, ex, request);
    }

    private ResponseEntity<ErrorResp> buildErrorResponse(HttpStatus status, String message, List<String> errors,
                                                         Exception ex, WebRequest request) {
        ErrorResp errorVm =
                new ErrorResp(status.toString(), status.getReasonPhrase(), message, errors);

        if (request instanceof ServletWebRequest servletWebRequest) {
            String path = servletWebRequest.getRequest().getServletPath();
            String method = servletWebRequest.getRequest().getMethod();
            log.error(ERROR_LOG_FORMAT, path, message, method);
        } else {
            log.error(message, ex);
        }
        return ResponseEntity.status(status).body(errorVm);
    }


    public ErrorResp parseFeignErrorBody(FeignException ex) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(ex.contentUTF8(), ErrorResp.class);
        } catch (Exception parseEx) {
            return new ErrorResp(
                    String.valueOf(ex.status()),
                    "Upstream Error",
                    "Could not parse error response from downstream service"
            );
        }
    }
}
