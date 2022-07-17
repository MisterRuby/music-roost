package ruby.musicroost.exception.schedule;

import org.springframework.http.HttpStatus;
import ruby.musicroost.exception.BusinessException;

public class ScheduleDifferentCourseException extends BusinessException {

    public static final String MESSAGE = "수강생과 선생님의 과목이 일치하지 않습니다.";

    public ScheduleDifferentCourseException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
