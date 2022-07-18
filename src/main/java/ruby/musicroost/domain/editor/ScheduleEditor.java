package ruby.musicroost.domain.editor;

import lombok.Builder;
import lombok.Getter;
import ruby.musicroost.domain.Teacher;

import java.time.LocalDateTime;

@Getter
public class ScheduleEditor {

    private Teacher teacher;
    private LocalDateTime time;

    @Builder
    public ScheduleEditor(Teacher teacher, LocalDateTime time) {
        this.teacher = teacher;
        this.time = time;
    }
}
