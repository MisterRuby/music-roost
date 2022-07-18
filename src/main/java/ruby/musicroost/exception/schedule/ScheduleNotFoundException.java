package ruby.musicroost.exception.schedule;

import org.springframework.http.HttpStatus;
import ruby.musicroost.exception.BusinessException;

public class ScheduleNotFoundException extends BusinessException {

    public static final String MESSAGE = "해당 스케쥴 정보를 찾을 수 없습니다.";

    public ScheduleNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
