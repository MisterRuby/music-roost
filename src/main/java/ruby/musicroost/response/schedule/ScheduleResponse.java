package ruby.musicroost.response.schedule;

import lombok.Getter;
import lombok.Setter;
import ruby.musicroost.domain.Schedule;
import ruby.musicroost.domain.Student;
import ruby.musicroost.domain.Teacher;

import java.time.format.DateTimeFormatter;

@Getter @Setter
public class ScheduleResponse {

    private Long id;
    private String time;
    private Long studentId;
    private String studentName;
    private Long teacherId;
    private String teacherName;
    private String course;

    public ScheduleResponse(Schedule schedule) {
        Student student = schedule.getStudent();
        Teacher teacher = schedule.getTeacher();

        this.id = schedule.getId();
        this.time = schedule.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.studentId = student.getId();
        this.studentName = student.getName();
        this.teacherId = teacher.getId();
        this.teacherName = teacher.getName();
        this.course = student.getCourse().name();
    }
}
