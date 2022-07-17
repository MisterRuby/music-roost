package ruby.musicroost.valid.validator;

import ruby.musicroost.valid.ScheduleTimePattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleTimeValidator implements ConstraintValidator<ScheduleTimePattern, String> {

    private static final String TIME_REGEXP = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12]\\d|3[01]) (1\\d|2[0-3]):([0-5]\\d)";


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(TIME_REGEXP);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
