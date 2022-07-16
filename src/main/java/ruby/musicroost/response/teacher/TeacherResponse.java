package ruby.musicroost.response.teacher;

import lombok.Getter;
import lombok.Setter;
import ruby.musicroost.domain.Teacher;

import java.time.LocalDate;

@Getter @Setter
public class TeacherResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String course;
    private LocalDate since;


    public TeacherResponse(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.email = teacher.getEmail();
        this.phoneNumber = teacher.getPhoneNumber();
        this.course = teacher.getCourse().name();
        this.since = teacher.getSince();
    }
}
