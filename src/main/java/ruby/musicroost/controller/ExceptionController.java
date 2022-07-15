package ruby.musicroost.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ruby.musicroost.response.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    public static final String BAD_REQUEST_MESSAGE = "잘못된 요청입니다.";

    /**
     * 검증 값 에러 처리
     * @param e
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse invalidRequestHandler(BindException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(BAD_REQUEST_MESSAGE)
                .build();

        e.getFieldErrors().forEach(errorResponse::addValidation);
        return errorResponse;
    }

    /**
     * 잘못된 값을 통해 조회 시 처리
     * @param e
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse invalidRequestHandler(IllegalArgumentException e) {

        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
    }
}
