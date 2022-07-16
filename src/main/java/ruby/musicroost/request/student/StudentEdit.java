package ruby.musicroost.request.student;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ruby.musicroost.valid.CoursePattern;
import ruby.musicroost.valid.EmailPattern;
import ruby.musicroost.valid.GradePattern;
import ruby.musicroost.valid.PhonePattern;

@Getter @Setter
@NoArgsConstructor
public class StudentEdit {

    @PhonePattern
    private String phoneNumber;
    @EmailPattern
    private String email;
    @CoursePattern
    private String course;
    @GradePattern
    private String grade;
    private Long teacherId;

    @Builder
    public StudentEdit(String phoneNumber, String email, String course, String grade, Long teacherId) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.course = course;
        this.grade = grade;
        this.teacherId = teacherId;
    }
}
