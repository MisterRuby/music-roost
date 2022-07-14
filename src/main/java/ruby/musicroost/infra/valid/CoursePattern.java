package ruby.musicroost.infra.valid;

import ruby.musicroost.infra.valid.validator.CourseValidator;

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
@Constraint(validatedBy = CourseValidator.class)
@Documented
public @interface CoursePattern {

    String message() default "수강 과목이 올바르지 않습니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}