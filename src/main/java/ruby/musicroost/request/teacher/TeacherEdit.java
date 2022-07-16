package ruby.musicroost.request.teacher;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ruby.musicroost.valid.*;

@Getter @Setter
@NoArgsConstructor
public class TeacherEdit {

    @NamePattern
    private String name;
    @PhonePattern
    private String phoneNumber;
    @EmailPattern
    private String email;
    @CoursePattern
    private String course;

    @Builder
    public TeacherEdit(String name, String phoneNumber, String email, String course) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.course = course;
    }
}
