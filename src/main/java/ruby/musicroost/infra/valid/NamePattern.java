package ruby.musicroost.infra.valid;

import ruby.musicroost.infra.valid.validator.NameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = NameValidator.class)
@Documented
public @interface NamePattern {

    String message() default "이름은 2~20자 한글, 영문 대소문자만 입력할 수 있습니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}