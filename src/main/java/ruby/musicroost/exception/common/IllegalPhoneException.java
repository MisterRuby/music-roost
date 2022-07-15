package ruby.musicroost.exception.common;

public class IllegalPhoneException extends IllegalArgumentException{
    public static final String MESSAGE = "핸드폰 번호 입력 형식이 올바르지 않습니다.";

    public IllegalPhoneException() {
        super(MESSAGE);
    }

    public IllegalPhoneException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
