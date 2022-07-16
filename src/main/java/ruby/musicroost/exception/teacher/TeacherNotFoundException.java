package ruby.musicroost.exception.teacher;

import org.springframework.http.HttpStatus;
import ruby.musicroost.exception.BusinessException;

public class TeacherNotFoundException extends BusinessException {

    public static final String MESSAGE = "해당 선생님의 정보를 찾을 수 없습니다.";

    public TeacherNotFoundException() {
        super(MESSAGE);
    }

    public TeacherNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}