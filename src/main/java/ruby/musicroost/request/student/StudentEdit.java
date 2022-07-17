package ruby.musicroost.request.student;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ruby.musicroost.valid.*;

@Getter @Setter
@NoArgsConstructor
public class StudentEdit {

    @NamePattern
    private String name;
    @PhonePattern
    private String phoneNumber;
    @EmailPattern
    private String email;
    @CoursePattern
    private String course;
    @GradePattern
    private String grade;

    @Builder
    public StudentEdit(String name, String phoneNumber, String email, String course, String grade) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.course = course;
        this.grade = grade;
    }
}
