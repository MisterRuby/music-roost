package ruby.musicroost.valid;

import ruby.musicroost.valid.validator.GradeSearchValidator;
import ruby.musicroost.valid.validator.GradeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = GradeSearchValidator.class)
@Documented
public @interface GradeSearchPattern {

    String MESSAGE = "등급이 올바르지 않습니다.";

    String message() default MESSAGE;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}