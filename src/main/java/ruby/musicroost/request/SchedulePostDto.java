package ruby.musicroost.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
@ToString
public class SchedulePostDto {

    @NotBlank(message = "수강생 이름이 비어있습니다.")
    private String studentName;
    @NotBlank(message = "선생님 이름이 비어있습니다.")
    private String teacherName;
    @NotBlank(message = "레슨 시작 시간이 입력되지 않았습니다.")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12]\\d|3[01]) (0\\d|1\\d|2[0-3]):(0\\d|[1-5]\\d)$",
            message = "레슨 시작 시간의 날짜 형식이 올바르지 않습니다.")
    private String startTime;
    @NotBlank(message = "레슨 종료 시간이 입력되지 않았습니다.")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12]\\d|3[01]) (0\\d|1\\d|2[0-3]):(0\\d|[1-5]\\d)$",
            message = "레슨 종료 시간의 날짜 형식이 올바르지 않습니다.")
    private String endTime;
}
