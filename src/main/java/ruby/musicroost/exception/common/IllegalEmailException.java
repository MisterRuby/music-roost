package ruby.musicroost.exception.common;

public class IllegalEmailException extends IllegalArgumentException{
    public static final String MESSAGE = "이메일 입력 형식이 올바르지 않습니다.";

    public IllegalEmailException() {
        super(MESSAGE);
    }

    public IllegalEmailException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
