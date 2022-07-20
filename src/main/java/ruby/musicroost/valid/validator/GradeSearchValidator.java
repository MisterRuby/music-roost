package ruby.musicroost.valid.validator;

import ruby.musicroost.domain.enums.Grade;
import ruby.musicroost.valid.GradeSearchPattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradeSearchValidator implements ConstraintValidator<GradeSearchPattern, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;
        Pattern pattern = Pattern.compile(getRegexpGrade());
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     * 등급 정규표현식 생성
     * @return
     */
    public static String getRegexpGrade() {
        Grade[] grades = Grade.values();
        StringBuilder builder = new StringBuilder();
        for (Grade grade : grades) {
            builder.append(grade.name()).append("|");
        }
        return builder.substring(0, builder.length());
    }
}