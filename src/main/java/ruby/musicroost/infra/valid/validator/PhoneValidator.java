package ruby.musicroost.infra.valid.validator;

import ruby.musicroost.infra.valid.PhonePattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<PhonePattern, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
