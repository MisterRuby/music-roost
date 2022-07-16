package ruby.musicroost.valid.validator;

import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.domain.enums.Grade;
import ruby.musicroost.valid.GradePattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradeValidator implements ConstraintValidator<GradePattern, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(getRegexpCourse());
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     * 등급 정규표현식 생성
     * @return
     */
    private String getRegexpCourse() {
        Grade[] grades = Grade.values();
        StringBuilder builder = new StringBuilder();
        for (Grade grade : grades) {
            builder.append(grade.name()).append("|");
        }
        return builder.substring(0, builder.length() - 1);
    }
}
