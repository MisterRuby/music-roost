package ruby.musicroost.infra.valid.validator;

import ruby.musicroost.infra.valid.CoursePattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseValidator implements ConstraintValidator<CoursePattern, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(getRegexpCourse());
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     * 수강과목 정규표현식 생성
     * @return
     */
    private String getRegexpCourse() {
        ruby.musicroost.domain.enums.Course[] courses = ruby.musicroost.domain.enums.Course.values();
        StringBuilder builder = new StringBuilder();
        for (ruby.musicroost.domain.enums.Course course : courses) {
            builder.append(course.name()).append("|");
        }
        return builder.substring(0, builder.length() - 1);
    }
}
