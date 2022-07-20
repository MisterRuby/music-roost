package ruby.musicroost.valid.validator;

import ruby.musicroost.request.schedule.enums.ScheduleOption;
import ruby.musicroost.valid.ScheduleOptionPattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleOptionValidator implements ConstraintValidator<ScheduleOptionPattern, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;
        Pattern pattern = Pattern.compile(getRegexpOption());
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    /**
     * 스케쥴 검색 옵션 정규표현식 생성
     * @return
     */
    public static String getRegexpOption() {
        ScheduleOption[] options = ScheduleOption.values();
        StringBuilder builder = new StringBuilder();
        for (ScheduleOption option : options) {
            builder.append(option.name()).append("|");
        }
        return builder.substring(0, builder.length() - 1);
    }
}
