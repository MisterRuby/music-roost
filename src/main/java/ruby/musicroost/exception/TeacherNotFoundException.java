package ruby.musicroost.exception;

public class TeacherNotFoundException extends IllegalArgumentException{
    public static final String MESSAGE = "해당 선생님의 정보를 찾을 수 없습니다.";

    public TeacherNotFoundException() {
        super(MESSAGE);
    }

    public TeacherNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
