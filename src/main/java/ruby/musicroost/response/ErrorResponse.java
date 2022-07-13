package ruby.musicroost.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 *     "code"       : code,
 *     "message"    : message,
 *     "validation" : {
 *          // 잘못된 필드에 대한 내용을 담아서 보내주어야 클라이언트 쪽에서 잘못된 값을 파악할 수 있다.
 *     }
 * }
 */
@RequiredArgsConstructor
@Getter @Setter
public class ErrorResponse {

    private final int code;
    private final String message;
    private final Map<String, String> validation = new HashMap<>();

    public void addValidation(FieldError fieldError) {
        this.validation.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
