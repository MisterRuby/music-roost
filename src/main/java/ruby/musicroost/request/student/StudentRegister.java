package ruby.musicroost.request.student;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ruby.musicroost.valid.*;

@Getter @Setter
@NoArgsConstructor
public class StudentRegister {

    @NamePattern
    private String name;
    @EmailPattern
    private String email;
    @PhonePattern
    private String phoneNumber;
    @CoursePattern
    private String course;
    @GradePattern
    private String grade;

    @Builder
    public StudentRegister(String name, String email, String phoneNumber, String course, String grade) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.course = course;
        this.grade = grade;
    }
}
