package ruby.musicroost.valid.validator;

import ruby.musicroost.domain.enums.Course;
import ruby.musicroost.valid.CourseSearchPattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseSearchValidator implements ConstraintValidator<CourseSearchPattern, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;
        Pattern pattern = Pattern.compile(getRegexpCourse());
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     * 수강과목 정규표현식 생성
     * @return
     */
    public static String getRegexpCourse() {
        Course[] courses = Course.values();
        StringBuilder builder = new StringBuilder();
        for (Course course : courses) {
            builder.append(course.name()).append("|");
        }
        return builder.substring(0, builder.length());
    }
}
