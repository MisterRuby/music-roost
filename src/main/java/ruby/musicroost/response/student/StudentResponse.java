package ruby.musicroost.response.student;

import lombok.Getter;
import lombok.Setter;
import ruby.musicroost.domain.Student;

import java.time.LocalDate;

@Getter @Setter
public class StudentResponse {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String course;
    private String grade;
    private LocalDate since;
    private Long teacherId;
    private String teacherName;

    public StudentResponse(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.email = student.getEmail();
        this.phoneNumber = student.getPhoneNumber();
        this.course = student.getCourse().name();
        this.grade = student.getGrade().name();
        this.since = student.getSince();
    }
}
