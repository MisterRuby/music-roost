package ruby.musicroost.infra.valid.validator;

import ruby.musicroost.infra.valid.NamePattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<NamePattern, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("^[가-힣a-zA-Z]{2,20}$");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
