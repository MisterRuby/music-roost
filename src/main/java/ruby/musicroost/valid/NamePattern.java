package ruby.musicroost.valid;

import ruby.musicroost.exception.common.IllegalNameException;
import ruby.musicroost.valid.validator.NameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = NameValidator.class)
@Documented
public @interface NamePattern {

    String message() default IllegalNameException.MESSAGE;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}