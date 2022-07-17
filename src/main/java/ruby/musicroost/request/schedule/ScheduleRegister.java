package ruby.musicroost.request.schedule;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ruby.musicroost.valid.ScheduleTimePattern;

@Getter @Setter
@NoArgsConstructor
public class ScheduleRegister {

    private Long teacherId;
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
