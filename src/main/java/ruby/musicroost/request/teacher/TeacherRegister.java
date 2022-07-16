package ruby.musicroost.request.teacher;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ruby.musicroost.valid.CoursePattern;
import ruby.musicroost.valid.EmailPattern;
import ruby.musicroost.valid.NamePattern;
import ruby.musicroost.valid.PhonePattern;

@Getter
@Setter
@NoArgsConstructor
public class TeacherRegister {

    @NamePattern
    private String name;
    @EmailPattern
    private String email;
    @PhonePattern
    private String phoneNumber;
    @CoursePattern
    private String course;

    @Builder
    public TeacherRegister(String name, String email, String phoneNumber, String course) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.course = course;
    }
}
