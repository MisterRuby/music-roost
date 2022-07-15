package ruby.musicroost.exception.student;

public class StudentNotFoundException extends IllegalArgumentException{

    public static final String MESSAGE = "해당 수강생의 정보를 찾을 수 없습니다.";

    public StudentNotFoundException() {
        super(MESSAGE);
    }

    public StudentNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
