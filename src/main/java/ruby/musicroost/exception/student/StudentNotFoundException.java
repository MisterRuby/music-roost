package ruby.musicroost.exception.student;

import org.springframework.http.HttpStatus;
import ruby.musicroost.exception.BusinessException;

public class StudentNotFoundException extends BusinessException {

    public static final String MESSAGE = "해당 수강생의 정보를 찾을 수 없습니다.";

    public StudentNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
