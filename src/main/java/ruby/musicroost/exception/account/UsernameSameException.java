package ruby.musicroost.exception.account;

import org.springframework.http.HttpStatus;
import ruby.musicroost.exception.BusinessException;

public class UsernameSameException extends BusinessException {

    public static final String MESSAGE = "해당 아이디로 등록된 계정이 이미 존재합니다.";

    public UsernameSameException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
