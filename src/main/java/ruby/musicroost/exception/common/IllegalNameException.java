package ruby.musicroost.exception.common;

public class IllegalNameException extends IllegalArgumentException{
    public static final String MESSAGE = "이름은 2~20자 한글, 영문 대소문자만 입력할 수 있습니다.";

    public IllegalNameException() {
        super(MESSAGE);
    }

    public IllegalNameException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
