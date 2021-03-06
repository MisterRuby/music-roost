package ruby.musicroost.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ruby.musicroost.exception.BusinessException;
import ruby.musicroost.response.ErrorResponse;

@RestControllerAdvice
public class ExceptionController {

    public static final String BIND_EXCEPTION_MESSAGE = "형식에 맞지 않는 값이 존재합니다.";


    /**
     * 검증 값 에러 처리
     * @param e
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse bindExceptionHandler(BindException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(BIND_EXCEPTION_MESSAGE)
                .build();

        e.getFieldErrors().forEach(errorResponse::addValidation);
        return errorResponse;
    }

    /**
     * 비즈니스 처리 중 예외 발생시 처리
     * @param e
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException e) {
        int status = e.getStatusCode();
        ErrorResponse body = ErrorResponse.builder()
                .code(status)
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(status).body(body);
    }
}
