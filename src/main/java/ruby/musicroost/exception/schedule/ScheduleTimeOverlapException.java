package ruby.musicroost.exception.schedule;

import org.springframework.http.HttpStatus;
import ruby.musicroost.exception.BusinessException;

public class ScheduleTimeOverlapException extends BusinessException {

    public static final String MESSAGE = "해당 시간에 예정된 스케쥴이 존재합니다. 스케쥴을 다시 확인해주세요.";

    public ScheduleTimeOverlapException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
