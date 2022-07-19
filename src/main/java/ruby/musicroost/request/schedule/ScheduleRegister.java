package ruby.musicroost.request.schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ruby.musicroost.valid.message.ValidMessage;
import ruby.musicroost.valid.ScheduleTimePattern;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@NoArgsConstructor
public class ScheduleRegister {
    @NotNull(message = ValidMessage.NOT_NULL)
    private Long teacherId;
    @NotNull(message = ValidMessage.NOT_NULL)
    private Long studentId;
    @ScheduleTimePattern
    private String time;

    @Builder
    public ScheduleRegister(Long teacherId, Long studentId, String time) {
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.time = time;
    }
}
