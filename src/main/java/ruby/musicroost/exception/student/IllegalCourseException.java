package ruby.musicroost.exception.student;

public class IllegalCourseException extends IllegalArgumentException{
    public static final String MESSAGE = "수강 과목이 올바르지 않습니다.";

    public IllegalCourseException() {
        super(MESSAGE);
    }

    public IllegalCourseException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
