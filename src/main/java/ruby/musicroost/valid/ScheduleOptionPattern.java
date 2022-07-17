package ruby.musicroost.valid;

import ruby.musicroost.valid.validator.ScheduleOptionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = ScheduleOptionValidator.class)
@Documented
public @interface ScheduleOptionPattern {

    String MESSAGE = "스케쥴 검색 종류가 올바르지 않습니다.";

    String message() default MESSAGE;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
