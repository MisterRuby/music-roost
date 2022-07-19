package ruby.musicroost.request.schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ruby.musicroost.valid.ScheduleTimePattern;
import ruby.musicroost.valid.message.ValidMessage;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleEdit {

    @NotNull(message = ValidMessage.NOT_NULL)
    private Long teacherId;
    @ScheduleTimePattern
    private String time;

    @Builder
    public ScheduleEdit(Long teacherId, String time) {
        this.teacherId = teacherId;
        this.time = time;
    }
}
